package com.branches.arquivo.service;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.arquivo.repository.ArquivoRepository;
import com.branches.exception.InternalServerError;
import com.branches.exception.NotFoundException;
import com.branches.obra.controller.CheckIfUserHasAccessToObraService;
import com.branches.relatorio.domain.RelatorioEntity;
import com.branches.relatorio.repository.projections.RelatorioWithObraProjection;
import com.branches.relatorio.service.CheckIfUserHasAccessToEditRelatorioService;
import com.branches.relatorio.service.GetRelatorioWithObraByIdExternoAndTenantIdService;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeleteArquivoDeRelatorioService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetRelatorioWithObraByIdExternoAndTenantIdService getRelatorioWithObraByIdExternoAndTenantIdService;
    private final ArquivoRepository arquivoRepository;
    private final CheckIfUserCanViewFotosService checkIfUserCanViewFotosService;
    private final CheckIfConfiguracaoDeRelatorioDaObraPermiteFoto checkIfConfiguracaoDeRelatorioDaObraPermiteFoto;
    private final CheckIfConfiguracaoDeRelatorioDaObraPermiteVideo checkIfConfiguracaoDeRelatorioDaObraPermiteVideo;
    private final CheckIfUserCanViewVideosService checkIfUserCanViewVideosService;
    private final CheckIfUserHasAccessToEditRelatorioService checkIfUserHasAccessToEditRelatorioService;
    private final CheckIfUserHasAccessToObraService checkIfUserHasAccessToObraService;

    public void execute(Long arquivoId, String relatorioExternalId, String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        RelatorioWithObraProjection relatorioWithObra = getRelatorioWithObraByIdExternoAndTenantIdService.execute(relatorioExternalId, tenantId);
        RelatorioEntity relatorio = relatorioWithObra.getRelatorio();

        checkIfUserHasAccessToObraService.execute(currentUserTenant, relatorio.getObraId());

        ArquivoEntity arquivoEntity = arquivoRepository.findByIdAndRelatorioId(arquivoId, relatorio.getId())
                .orElseThrow(() -> new NotFoundException("Arquivo de relatorio nao encontrado com o id: " + arquivoId));

        switch (arquivoEntity.getTipoArquivo()) {
            case FOTO -> {
                checkIfConfiguracaoDeRelatorioDaObraPermiteFoto.execute(relatorioWithObra);
                checkIfUserCanViewFotosService.execute(currentUserTenant);
            }
            case VIDEO -> {
                checkIfConfiguracaoDeRelatorioDaObraPermiteVideo.execute(relatorioWithObra);
                checkIfUserCanViewVideosService.execute(currentUserTenant);
            }

            default -> throw new InternalServerError("Tipo ainda não implementado para exclusão de arquivo de relatorio: " + arquivoEntity.getTipoArquivo());

            //todo: quando implementar novos tipos de arquivo, adicionar os devidos cases aqui
        }
        checkIfUserHasAccessToEditRelatorioService.execute(currentUserTenant, relatorio.getStatus());

        arquivoRepository.delete(arquivoEntity);
    }
}
