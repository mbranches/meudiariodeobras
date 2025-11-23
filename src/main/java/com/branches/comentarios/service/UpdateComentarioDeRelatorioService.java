package com.branches.comentarios.service;

import com.branches.comentarios.model.ComentarioDeRelatorioEntity;
import com.branches.exception.ForbiddenException;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.service.GetObraByIdAndTenantIdService;
import com.branches.relatorio.domain.RelatorioEntity;
import com.branches.relatorio.dto.request.CampoPersonalizadoRequest;
import com.branches.relatorio.dto.request.UpdateComentarioDeRelatorioRequest;
import com.branches.relatorio.repository.ComentarioDeRelatorioRepository;
import com.branches.relatorio.service.CheckIfUserHasAccessToEditRelatorioService;
import com.branches.relatorio.service.GetRelatorioByIdExternoAndTenantIdService;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UpdateComentarioDeRelatorioService {
    private final ComentarioDeRelatorioRepository comentarioDeRelatorioRepository;
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final CheckIfUserHasAccessToEditRelatorioService checkIfUserHasAccessToEditRelatorioService;
    private final GetRelatorioByIdExternoAndTenantIdService getRelatorioByIdExternoAndTenantIdService;
    private final GetObraByIdAndTenantIdService getObraByIdAndTenantIdService;
    private final GetComentarioDeRelatorioByIdAndRelatorioIdService getComentarioDeRelatorioByIdAndRelatorioIdService;

    public void execute(UpdateComentarioDeRelatorioRequest request, Long id, String relatorioExternalId, String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity userTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserHasAccessToEditRelatorioService.execute(userTenant);

        RelatorioEntity relatorio = getRelatorioByIdExternoAndTenantIdService.execute(relatorioExternalId, tenantId);

        checkIfConfiguracaoDeRelatorioDaObraPermiteComentario(relatorio, tenantId);

        checkIfUserCanViewComentarios(userTenant);

        ComentarioDeRelatorioEntity entity = getComentarioDeRelatorioByIdAndRelatorioIdService.execute(id, relatorio.getId());
        entity.setDescricao(request.descricao());

        List<CampoPersonalizadoRequest> campoPersonalizadoRequest = request.camposPersonalizados() != null
                ? request.camposPersonalizados()
                : List.of();
        entity.getCamposPersonalizados().clear();
        entity.getCamposPersonalizados().addAll(
                campoPersonalizadoRequest.stream().map(c -> c.toEntity(tenantId)).toList()
        );

        comentarioDeRelatorioRepository.save(entity);
    }

    private void checkIfUserCanViewComentarios(UserTenantEntity userTenant) {
        if (userTenant.getAuthorities().getItensDeRelatorio().getComentarios()) return;

        throw new ForbiddenException();
    }

    private void checkIfConfiguracaoDeRelatorioDaObraPermiteComentario(RelatorioEntity relatorio, Long tenantId) {
        ObraEntity obra = getObraByIdAndTenantIdService.execute(relatorio.getObraId(), tenantId);

        if (obra.getConfiguracaoRelatorios().getShowComentarios()) return;

        throw new ForbiddenException();
    }
}
