package com.branches.relatorio.service;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import com.branches.comentarios.model.ComentarioDeRelatorioEntity;
import com.branches.equipamento.domain.EquipamentoDeRelatorioEntity;
import com.branches.maodeobra.domain.MaoDeObraDeRelatorioEntity;
import com.branches.material.domain.MaterialDeRelatorioEntity;
import com.branches.ocorrencia.domain.OcorrenciaDeRelatorioEntity;
import com.branches.relatorio.domain.ArquivoDeRelatorioDeUsuarioEntity;
import com.branches.relatorio.domain.AssinaturaDeRelatorioEntity;
import com.branches.relatorio.repository.ArquivoDeRelatorioDeUsuarioRepository;
import com.branches.relatorio.repository.RelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import com.branches.usertenant.domain.UserTenantEntity;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Gera arquivos de relatórios para um único usuário para múltiplas obras, processando em paralelo para melhorar a performance.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GenerateRelatorioFileOfObrasForSingleUserService {
    private final RelatorioRepository relatorioRepository;
    private final GetRelatorioIdAndOcorrenciasMapService getRelatorioIdAndOcorrenciasMapService;
    private final GetRelatorioIdAndAtividadesMapService getRelatorioIdAndAtividadesMapService;
    private final GetRelatorioIdAndEquipamentosMapService getRelatorioIdAndEquipamentosMapService;
    private final GetRelatorioIdAndMaoDeObraMapService getRelatorioIdAndMaoDeObraMapService;
    private final GetRelatorioIdAndComentariosMapService getRelatorioIdAndComentariosMapService;
    private final GetRelatorioIdAndMateriaisMapService getRelatorioIdAndMateriaisMapService;
    private final GetRelatorioIdAndAssinaturasMapService getRelatorioIdAndAssinaturasMapService;
    private final GetRelatorioIdAndArquivosMapService getRelatorioIdAndArquivosMapService;
    private final ProcessRelatorioFileToUsersService processRelatorioFileToUsersService;
    private final ArquivoDeRelatorioDeUsuarioRepository arquivoDeRelatorioDeUsuarioRepository;
    private final ExecutorService pdfExecutor = Executors.newFixedThreadPool(20); // Executor para processamento paralelo, são reservados 20 threads

    @PreDestroy
    public void shutdown() {
        log.info("Encerrando executor de PDFs para single user");
        pdfExecutor.shutdown();
        try {
            if (!pdfExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                pdfExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            log.warn("Shutdown do executor foi interrompido. Forçando encerramento imediato.");
            pdfExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Async
    public void execute(UserTenantEntity userTenant, List<Long> obrasIds) {
        log.info("Iniciando geração de arquivos de relatórios para o usuário ID {} e obras IDs {}", userTenant.getUser().getId(), obrasIds);
        HashSet<Long> obrasIdsSet = new HashSet<>(userTenant.getObrasPermitidasIds());
        if (!obrasIdsSet.containsAll(obrasIds)) {
            log.info("Foi passada um ou mais obras que o usuário ID {} não tem permissão. Abortando geração de arquivos de relatórios.", userTenant.getUser().getId());

            return;
        }

        List<RelatorioDetailsProjection> relatoriosDetails = relatorioRepository.findAllDetailsWithoutPdfUrlByObraIdInAndTenantId(obrasIdsSet, userTenant.getTenantId());
        Map<Long, List<OcorrenciaDeRelatorioEntity>> relatorioIdAndOcorrenciasMap = getRelatorioIdAndOcorrenciasMapService.execute(relatoriosDetails);
        Map<Long, List<AtividadeDeRelatorioEntity>> relatorioIdAndAtividadesMap = getRelatorioIdAndAtividadesMapService.execute(relatoriosDetails);
        Map<Long, List<EquipamentoDeRelatorioEntity>> relatorioIdAndEquipamentosMap = getRelatorioIdAndEquipamentosMapService.execute(relatoriosDetails);
        Map<Long, List<MaoDeObraDeRelatorioEntity>> relatorioIdAndMaoDeObraMap = getRelatorioIdAndMaoDeObraMapService.execute(relatoriosDetails);
        Map<Long, List<ComentarioDeRelatorioEntity>> relatorioIdAndComentariosMap = getRelatorioIdAndComentariosMapService.execute(relatoriosDetails);
        Map<Long, List<MaterialDeRelatorioEntity>> relatorioIdAndMateriaisMap = getRelatorioIdAndMateriaisMapService.execute(relatoriosDetails);
        Map<Long, List<AssinaturaDeRelatorioEntity>> relatorioIdAndAssinaturasMap = getRelatorioIdAndAssinaturasMapService.execute(relatoriosDetails);
        Map<Long, List<ArquivoEntity>> relatorioIdAndArquivosMap = getRelatorioIdAndArquivosMapService.execute(relatoriosDetails);

        List<ArquivoDeRelatorioDeUsuarioEntity> allArquivosToSave = new ArrayList<>();
        List<CompletableFuture<Void>> geracaoAsync = relatoriosDetails.stream()
                .map(relatorio -> CompletableFuture.runAsync(() -> {
                    try {
                        List<ArquivoDeRelatorioDeUsuarioEntity> arquivosToSave = processRelatorioFileToUsersService.execute(
                                relatorio,
                                List.of(userTenant),
                                new HashMap<>(),
                                relatorioIdAndOcorrenciasMap.get(relatorio.getId()),
                                relatorioIdAndAtividadesMap.get(relatorio.getId()),
                                relatorioIdAndEquipamentosMap.get(relatorio.getId()),
                                relatorioIdAndMaoDeObraMap.get(relatorio.getId()),
                                relatorioIdAndComentariosMap.get(relatorio.getId()),
                                relatorioIdAndMateriaisMap.get(relatorio.getId()),
                                relatorioIdAndAssinaturasMap.get(relatorio.getId()),
                                relatorioIdAndArquivosMap.get(relatorio.getId())
                        );

                        synchronized (allArquivosToSave) {
                            allArquivosToSave.addAll(arquivosToSave);
                        }
                    } catch (Exception e) {
                        log.error("Erro ao gerar arquivo de relatorio para o relatorio ID {}: {}", relatorio.getId(), e.getMessage());
                    }
                }, pdfExecutor)).toList();

        CompletableFuture.allOf(geracaoAsync.toArray(new CompletableFuture[0])).join();

        arquivoDeRelatorioDeUsuarioRepository.saveAll(allArquivosToSave);
        log.info("Geração de arquivos de relatórios concluída para o usuário ID {}. Total de arquivos salvos: {}", userTenant.getUser().getId(), allArquivosToSave.size());
    }
}
