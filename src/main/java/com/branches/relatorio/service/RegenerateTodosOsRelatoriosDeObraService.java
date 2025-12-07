package com.branches.relatorio.service;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import com.branches.comentarios.model.ComentarioDeRelatorioEntity;
import com.branches.equipamento.domain.EquipamentoDeRelatorioEntity;
import com.branches.maodeobra.domain.MaoDeObraDeRelatorioEntity;
import com.branches.material.domain.MaterialDeRelatorioEntity;
import com.branches.obra.domain.ObraEntity;
import com.branches.ocorrencia.domain.OcorrenciaDeRelatorioEntity;
import com.branches.relatorio.domain.ArquivoDeRelatorioDeUsuarioEntity;
import com.branches.relatorio.domain.AssinaturaDeRelatorioEntity;
import com.branches.relatorio.repository.ArquivoDeRelatorioDeUsuarioRepository;
import com.branches.relatorio.repository.RelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.repository.UserTenantRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Serviço para gerar todos os relatórios pra todos os usuários de uma obra específica.]
 * Isso é feito de forma Async
 * São processados 20 relatórios por vez, em paralelo, para otimizar o uso de recursos.
 * Caso um novo processamento para uma obra que ja esta processando, o processamento anterior é cancelado.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegenerateTodosOsRelatoriosDeObraService {
    private final RelatorioRepository relatorioRepository;
    private final ProcessRelatorioFileToUsersService processRelatorioFileToUsersService;
    private final UserTenantRepository userTenantRepository;
    private final ArquivoDeRelatorioDeUsuarioRepository arquivoDeRelatorioDeUsuarioRepository;
    private final GetRelatorioIdAndOcorrenciasMapService getRelatorioIdAndOcorrenciasMapService;
    private final GetRelatorioIdAndAtividadesMapService getRelatorioIdAndAtividadesMapService;
    private final GetRelatorioIdAndEquipamentosMapService getRelatorioIdAndEquipamentosMapService;
    private final GetRelatorioIdAndMaoDeObraMapService getRelatorioIdAndMaoDeObraMapService;
    private final GetRelatorioIdAndComentariosMapService getRelatorioIdAndComentariosMapService;
    private final GetRelatorioIdAndMateriaisMapService getRelatorioIdAndMateriaisMapService;
    private final GetRelatorioIdAndAssinaturasMapService getRelatorioIdAndAssinaturasMapService;
    private final GetRelatorioIdAndArquivosMapService getRelatorioIdAndArquivosMapService;
    private final GetRelatorioIdAndMapOfUserIdAndArquivoDeRelatorioMapService getRelatorioIdAndMapOfUserIdAndArquivoDeRelatorioMapService;

    // Map para rastrear processamentos em andamento por obra
    private final ExecutorService pdfExecutor = Executors.newFixedThreadPool(20); // Executor para processamento paralelo, são reservados 20 threads
    private final Map<Long, CompletableFuture<Void>> processamentosEmAndamento = new ConcurrentHashMap<>();

    @PreDestroy
    public void shutdown() {
        log.info("Encerrando executor de PDFs");
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
    public void execute(ObraEntity obra) {
        Long obraId = obra.getId();

        // Cancelar processamento anterior se existir
        CompletableFuture<Void> processamentoAnterior = processamentosEmAndamento.get(obraId);
        if (processamentoAnterior != null && !processamentoAnterior.isDone()) {
            log.warn("Cancelando processamento em andamento para obra ID: {}. Novo processamento será iniciado.", obraId);
            processamentoAnterior.cancel(true);
        }

        // Criar novo CompletableFuture para rastrear este processamento
        CompletableFuture<Void> novoProcessamento = CompletableFuture.runAsync(() -> {
            try {
                processarRelatorios(obra);
            } catch (Exception e) {
                if (e instanceof CancellationException) {
                    log.info("Processamento da obra ID {} foi cancelado", obraId);
                } else {
                    log.error("Erro ao processar relatórios da obra ID {}", obraId, e);
                }
            } finally {
                processamentosEmAndamento.remove(obraId);
            }
        });

        processamentosEmAndamento.put(obraId, novoProcessamento);
    }

    private void processarRelatorios(ObraEntity obra) {
        Long obraId = obra.getId();
        log.info("Iniciando geração de todos os relatórios da obra ID: {}", obraId);
        List<RelatorioDetailsProjection> relatorios = relatorioRepository.findAllDetailsWithoutPdfLinkByObraId(obraId);

        Map<Long, List<OcorrenciaDeRelatorioEntity>> mapRelatorioIdAndOcorrencias = getRelatorioIdAndOcorrenciasMapService.execute(relatorios);
        Map<Long, List<AtividadeDeRelatorioEntity>> mapRelatorioIdAndAtividades = getRelatorioIdAndAtividadesMapService.execute(relatorios);
        Map<Long, List<EquipamentoDeRelatorioEntity>> mapRelatorioIdAndEquipamentos = getRelatorioIdAndEquipamentosMapService.execute(relatorios);
        Map<Long, List<MaoDeObraDeRelatorioEntity>> mapRelatorioIdAndMaoDeObra = getRelatorioIdAndMaoDeObraMapService.execute(relatorios);
        Map<Long, List<ComentarioDeRelatorioEntity>> mapRelatorioIdAndComentarios = getRelatorioIdAndComentariosMapService.execute(relatorios);
        Map<Long, List<MaterialDeRelatorioEntity>> mapRelatorioIdAndMateriais = getRelatorioIdAndMateriaisMapService.execute(relatorios);
        Map<Long, List<AssinaturaDeRelatorioEntity>> mapRelatorioIdAndAssinaturas = getRelatorioIdAndAssinaturasMapService.execute(relatorios);
        Map<Long, List<ArquivoEntity>> mapRelatorioIdAndArquivos = getRelatorioIdAndArquivosMapService.execute(relatorios);

        List<UserTenantEntity> userTenantsWithAccessToObra = userTenantRepository.findAllByTenantIdAndUserHasAccessToObraId(obra.getTenantId(), obraId);
        Map<Long, Map<Long, ArquivoDeRelatorioDeUsuarioEntity>> mapRelatorioIdAndMapUserAndHisArquivoDeRelatorio = getRelatorioIdAndMapOfUserIdAndArquivoDeRelatorioMapService.execute(relatorios, userTenantsWithAccessToObra);

        List<ArquivoDeRelatorioDeUsuarioEntity> allArquivosToSave = new ArrayList<>();

        log.info("Processando {} relatórios em lotes...", relatorios.size());
        // Processar relatórios em lotes de 20
        int batchSize = 20;
        AtomicInteger batchNumber = new AtomicInteger(0);
        for (int i = 0; i < relatorios.size(); i += batchSize) {
            // Verificar se o processamento foi cancelado
            if (Thread.currentThread().isInterrupted()) {
                log.warn("Processamento da obra ID {} foi interrompido no lote {}", obraId, batchNumber.get());
                throw new CancellationException("Processamento cancelado");
            }

            log.info("Processando lote {}", batchNumber.getAndIncrement());
            int endIndex = Math.min(i + batchSize, relatorios.size());
            List<RelatorioDetailsProjection> batch = relatorios.subList(i, endIndex);

            List<CompletableFuture<Void>> batchFutures = batch.stream()
                    .map(relatorio -> CompletableFuture.runAsync(() -> {
                        // Verificar interrupção antes de processar cada relatório
                        if (Thread.currentThread().isInterrupted()) {
                            throw new CancellationException("Processamento cancelado");
                        }

                        try {
                            List<ArquivoDeRelatorioDeUsuarioEntity> arquivosToSave = processRelatorioFileToUsersService.execute(
                                    relatorio,
                                    userTenantsWithAccessToObra,
                                    mapRelatorioIdAndMapUserAndHisArquivoDeRelatorio.get(relatorio.getId()),
                                    mapRelatorioIdAndOcorrencias.get(relatorio.getId()),
                                    mapRelatorioIdAndAtividades.get(relatorio.getId()),
                                    mapRelatorioIdAndEquipamentos.get(relatorio.getId()),
                                    mapRelatorioIdAndMaoDeObra.get(relatorio.getId()),
                                    mapRelatorioIdAndComentarios.get(relatorio.getId()),
                                    mapRelatorioIdAndMateriais.get(relatorio.getId()),
                                    mapRelatorioIdAndAssinaturas.get(relatorio.getId()),
                                    mapRelatorioIdAndArquivos.get(relatorio.getId())
                            );

                            synchronized (allArquivosToSave) {
                                allArquivosToSave.addAll(arquivosToSave);
                            }
                        } catch (CancellationException e) {
                            throw e; // Re-lançar para propagar o cancelamento
                        } catch (Exception e) {
                            log.error("Erro ao processar relatório ID {}: {}", relatorio.getId(), e.getMessage());
                        }
                    }, pdfExecutor)).toList();

            try {
                CompletableFuture.allOf(batchFutures.toArray(new CompletableFuture[0])).join();
            } catch (CancellationException | CompletionException e) {
                if (e.getCause() instanceof CancellationException || e instanceof CancellationException) {
                    log.warn("Lote {} cancelado para obra ID {}", batchNumber.get() - 1, obraId);
                    throw new CancellationException("Processamento cancelado");
                }
                throw e;
            }
        }
        log.info("Relatórios processados. Salvando {} arquivos de relatórios de usuários no banco de dados...", allArquivosToSave.size());
        arquivoDeRelatorioDeUsuarioRepository.saveAll(allArquivosToSave);
        log.info("Geração de todos os relatórios da obra ID {} concluída com sucesso. Total de arquivos salvos: {}", obra.getId(), allArquivosToSave.size());
    }
}
