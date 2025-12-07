package com.branches.relatorio.service;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.arquivo.repository.ArquivoRepository;
import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import com.branches.atividade.repository.AtividadeDeRelatorioRepository;
import com.branches.comentarios.model.ComentarioDeRelatorioEntity;
import com.branches.comentarios.repository.ComentarioDeRelatorioRepository;
import com.branches.equipamento.domain.EquipamentoDeRelatorioEntity;
import com.branches.equipamento.repository.EquipamentoDeRelatorioRepository;
import com.branches.exception.NotFoundException;
import com.branches.maodeobra.domain.MaoDeObraDeRelatorioEntity;
import com.branches.maodeobra.repository.MaoDeObraDeRelatorioRepository;
import com.branches.material.domain.MaterialDeRelatorioEntity;
import com.branches.material.repository.MaterialDeRelatorioRepository;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.service.GetObraByIdAndTenantIdService;
import com.branches.ocorrencia.domain.OcorrenciaDeRelatorioEntity;
import com.branches.ocorrencia.repository.OcorrenciaDeRelatorioRepository;
import com.branches.relatorio.domain.ArquivoDeRelatorioDeUsuarioEntity;
import com.branches.relatorio.domain.AssinaturaDeRelatorioEntity;
import com.branches.relatorio.repository.ArquivoDeRelatorioDeUsuarioRepository;
import com.branches.relatorio.repository.AssinaturaDeRelatorioRepository;
import com.branches.relatorio.repository.RelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import com.branches.tenant.domain.TenantEntity;
import com.branches.tenant.service.GetTenantByIdService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.repository.UserTenantRepository;
import com.branches.utils.ItemRelatorio;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Async
@RequiredArgsConstructor
@Service
public class GenerateRelatorioFileToUsersService {
    private final RelatorioRepository relatorioRepository;
    private final UserTenantRepository userTenantRepository;
    private final OcorrenciaDeRelatorioRepository ocorrenciaDeRelatorioRepository;
    private final AtividadeDeRelatorioRepository atividadeDeRelatorioRepository;
    private final EquipamentoDeRelatorioRepository equipamentoDeRelatorioRepository;
    private final MaoDeObraDeRelatorioRepository maoDeObraDeRelatorioRepository;
    private final ComentarioDeRelatorioRepository comentarioDeRelatorioRepository;
    private final MaterialDeRelatorioRepository materialDeRelatorioRepository;
    private final ArquivoRepository arquivoRepository;
    private final AssinaturaDeRelatorioRepository assinaturaDeRelatorioRepository;
    private final GetTenantByIdService getTenantByIdService;
    private final GetObraByIdAndTenantIdService getObraByIdAndTenantIdService;
    private final GenerateRelatorioToUserService generateRelatorioToUserService;
    private final ArquivoDeRelatorioDeUsuarioRepository arquivoDeRelatorioDeUsuarioRepository;

    public void execute(Long relatorioId) {
        RelatorioDetailsProjection details = getRelatorioDetailsOrThrow(relatorioId);

        List<UserTenantEntity> userTenants = getUserTenantsWithAccessToRelatorio(details.getTenantId(), details.getObraId());

        processFilesToUsers(details, userTenants);
    }

    public void executeOnlyToNecessaryUsers(Long relatorioId, ItemRelatorio itemAtualizado) {
        RelatorioDetailsProjection details = getRelatorioDetailsOrThrow(relatorioId);

        List<UserTenantEntity> userTenants = getUserTenantsWithAccessToRelatorio(details.getTenantId(), details.getObraId());

        List<UserTenantEntity> necessaryUsers = userTenants.stream()
                .filter(userTenant -> {
                    switch (itemAtualizado) {
                        case OCORRENCIAS -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getOcorrencias();
                        }
                        case ATIVIDADES -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getAtividades();
                        }
                        case EQUIPAMENTOS -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getEquipamentos();
                        }
                        case MAO_DE_OBRA -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getMaoDeObra();
                        }
                        case COMENTARIOS -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getComentarios();
                        }
                        case MATERIAIS -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getMateriais();
                        }
                        case FOTOS -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getFotos();
                        }
                        case VIDEOS -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getVideos();
                        }
                        case CONDICAO_CLIMATICA -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getCondicaoDoClima();
                        }
                        case HORARIO_DE_TRABALHO -> {
                            return userTenant.getAuthorities().getItensDeRelatorio().getHorarioDeTrabalho();
                        }
                        default -> {
                            return false;
                        }
                    }
                }).toList();

        processFilesToUsers(details, necessaryUsers);
    }

    private RelatorioDetailsProjection getRelatorioDetailsOrThrow(Long relatorioId) {
        return relatorioRepository.findDetailsWithoutPdfLinkById(relatorioId)
                .orElseThrow(() -> new NotFoundException("Relatório não encontrado com o id: " + relatorioId));
    }

    private List<UserTenantEntity> getUserTenantsWithAccessToRelatorio(Long tenantId, Long obraId) {
        return userTenantRepository.findAllByTenantIdAndUserHasAccessToObraId(
                tenantId,
                obraId
        );
    }

    private void processFilesToUsers(
            RelatorioDetailsProjection details,
            List<UserTenantEntity> userTenants
    ) {
        ObraEntity obra = getObraByIdAndTenantIdService.execute(details.getObraId(), details.getTenantId());

        TenantEntity tenantEntity = getTenantByIdService.execute(details.getTenantId());

        Long relatorioId = details.getId();

        Map<Long, ArquivoDeRelatorioDeUsuarioEntity> mapUserIdAndArquivoDeRelatorio = getMapOfExistingArquivoDeRelatorioToUserId(userTenants, relatorioId);

        boolean relatorioAllowsOcorrencias = details.getShowOcorrencias();
        boolean relatorioAllowsAtividades = details.getShowAtividades();
        boolean relatorioAllowsEquipamentos = details.getShowEquipamentos();
        boolean relatorioAllowsMaoDeObra = details.getShowMaoDeObra();
        boolean relatorioAllowsComentarios = details.getShowComentarios();
        boolean relatorioAllowsMateriais = details.getShowMateriais();
        boolean relatorioAllowsFotos = details.getShowFotos();
        boolean relatorioAllowsVideos = details.getShowVideos();

        List<OcorrenciaDeRelatorioEntity> ocorrencias = relatorioAllowsOcorrencias ? ocorrenciaDeRelatorioRepository.findAllByRelatorioId(relatorioId) : Collections.emptyList();
        List<AtividadeDeRelatorioEntity> atividades = relatorioAllowsAtividades ?atividadeDeRelatorioRepository.findAllByRelatorioId(relatorioId) : Collections.emptyList();
        List<EquipamentoDeRelatorioEntity> equipamentos = relatorioAllowsEquipamentos ? equipamentoDeRelatorioRepository.findAllByRelatorioId(relatorioId) : Collections.emptyList();
        List<MaoDeObraDeRelatorioEntity> maoDeObra = relatorioAllowsMaoDeObra ? maoDeObraDeRelatorioRepository.findAllByRelatorioId(relatorioId) : Collections.emptyList();
        List<ComentarioDeRelatorioEntity> comentarios = relatorioAllowsComentarios ? comentarioDeRelatorioRepository.findAllByRelatorioId(relatorioId) : Collections.emptyList();
        List<MaterialDeRelatorioEntity> materiais = relatorioAllowsMateriais ? materialDeRelatorioRepository.findAllByRelatorioId(relatorioId) : Collections.emptyList();
        List<AssinaturaDeRelatorioEntity> assinaturas = assinaturaDeRelatorioRepository.findAllByRelatorioId(relatorioId);

        List<ArquivoEntity> arquivos = arquivoRepository.findAllByRelatorioId(relatorioId);

        List<ArquivoEntity> fotos = relatorioAllowsFotos ? arquivos.stream().filter(ArquivoEntity::getIsFoto).toList() : Collections.emptyList();
        List<ArquivoEntity> videos = relatorioAllowsVideos ? arquivos.stream().filter(ArquivoEntity::getIsVideo).toList() : Collections.emptyList();

        List<ArquivoDeRelatorioDeUsuarioEntity> arquivoDeRelatorioDeUsuarioToSaveList = new ArrayList<>();

        List<CompletableFuture<Void>> geracoesDosRelatoriosAsync = userTenants.stream()
                .map(userTenant -> CompletableFuture.runAsync(() ->
                        generateFile(
                                details,
                                tenantEntity,
                                obra,
                                userTenant,
                                ocorrencias,
                                atividades,
                                equipamentos,
                                maoDeObra,
                                comentarios,
                                materiais,
                                fotos,
                                videos,
                                assinaturas,
                                relatorioId,
                                mapUserIdAndArquivoDeRelatorio,
                                arquivoDeRelatorioDeUsuarioToSaveList
                        )
                ))
                .toList();

        CompletableFuture.allOf(geracoesDosRelatoriosAsync.toArray(new CompletableFuture[0])).join();

        arquivoDeRelatorioDeUsuarioRepository.saveAll(arquivoDeRelatorioDeUsuarioToSaveList);
    }

    private void generateFile(
            RelatorioDetailsProjection details,
            TenantEntity tenantEntity,
            ObraEntity obra,
            UserTenantEntity userTenant,
            List<OcorrenciaDeRelatorioEntity> ocorrencias,
            List<AtividadeDeRelatorioEntity> atividades,
            List<EquipamentoDeRelatorioEntity> equipamentos,
            List<MaoDeObraDeRelatorioEntity> maoDeObra,
            List<ComentarioDeRelatorioEntity> comentarios,
            List<MaterialDeRelatorioEntity> materiais,
            List<ArquivoEntity> fotos,
            List<ArquivoEntity> videos,
            List<AssinaturaDeRelatorioEntity> assinaturas,
            Long relatorioId,
            Map<Long, ArquivoDeRelatorioDeUsuarioEntity> mapUserIdAndArquivoDeRelatorio,
            List<ArquivoDeRelatorioDeUsuarioEntity> arquivoDeRelatorioDeUsuarioToSaveList
    ) {
        var userPermissionsItensDeRelatorio = userTenant.getAuthorities().getItensDeRelatorio();

        boolean userCanViewOcorrencias = userPermissionsItensDeRelatorio.getOcorrencias();
        boolean userCanViewAtividades = userPermissionsItensDeRelatorio.getAtividades();
        boolean userCanViewEquipamentos = userPermissionsItensDeRelatorio.getEquipamentos();
        boolean userCanViewMaoDeObra = userPermissionsItensDeRelatorio.getMaoDeObra();
        boolean userCanViewComentarios = userPermissionsItensDeRelatorio.getComentarios();
        boolean userCanViewMateriais = userPermissionsItensDeRelatorio.getMateriais();
        boolean userCanViewFotos = userPermissionsItensDeRelatorio.getFotos();
        boolean userCanViewVideos = userPermissionsItensDeRelatorio.getVideos();

        List<OcorrenciaDeRelatorioEntity> ocorrenciasDoRelatorio = userCanViewOcorrencias ? ocorrencias : Collections.emptyList();
        List<AtividadeDeRelatorioEntity> atividadesDoRelatorio = userCanViewAtividades ? atividades : Collections.emptyList();
        List<EquipamentoDeRelatorioEntity> equipamentosDoRelatorio = userCanViewEquipamentos ? equipamentos : Collections.emptyList();
        List<MaoDeObraDeRelatorioEntity> maoDeObraDoRelatorio = userCanViewMaoDeObra ? maoDeObra : Collections.emptyList();
        List<ComentarioDeRelatorioEntity> comentariosDoRelatorio = userCanViewComentarios ? comentarios : Collections.emptyList();
        List<MaterialDeRelatorioEntity> materiaisDoRelatorio = userCanViewMateriais ? materiais : Collections.emptyList();
        List<ArquivoEntity> fotosDoRelatorio = userCanViewFotos ? fotos : Collections.emptyList();
        List<ArquivoEntity> videosDoRelatorio = userCanViewVideos ? videos : Collections.emptyList();

        String url = generateRelatorioToUserService.execute(
                details,
                tenantEntity,
                obra,
                userTenant,
                ocorrenciasDoRelatorio,
                atividadesDoRelatorio,
                equipamentosDoRelatorio,
                maoDeObraDoRelatorio,
                comentariosDoRelatorio,
                materiaisDoRelatorio,
                fotosDoRelatorio,
                videosDoRelatorio,
                assinaturas
        );

        if (mapUserIdAndArquivoDeRelatorio.containsKey(userTenant.getUser().getId())) return;

        ArquivoDeRelatorioDeUsuarioEntity toSave = ArquivoDeRelatorioDeUsuarioEntity.builder()
                .arquivoUrl(url)
                .relatorioId(relatorioId)
                .userId(userTenant.getUser().getId())
                .build();

        arquivoDeRelatorioDeUsuarioToSaveList.add(toSave);
    }

    private Map<Long, ArquivoDeRelatorioDeUsuarioEntity> getMapOfExistingArquivoDeRelatorioToUserId(List<UserTenantEntity> userTenants, Long relatorioId) {
        List<ArquivoDeRelatorioDeUsuarioEntity> existenteArquivoDeRelatorioDosUsers = arquivoDeRelatorioDeUsuarioRepository.findAllByRelatorioIdAndUserIdIn(
                relatorioId,
                userTenants.stream().map(ut -> ut.getUser().getId()).toList());

        return existenteArquivoDeRelatorioDosUsers.stream()
                .collect(Collectors.toMap(ArquivoDeRelatorioDeUsuarioEntity::getUserId, arq -> arq));
    }
}
