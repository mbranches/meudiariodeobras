package com.branches.material.service;

import com.branches.exception.ForbiddenException;
import com.branches.material.domain.MaterialDeRelatorioEntity;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.service.GetObraByIdAndTenantIdService;
import com.branches.relatorio.domain.RelatorioEntity;
import com.branches.material.dto.request.UpdateMaterialDeRelatorioRequest;
import com.branches.relatorio.repository.MaterialDeRelatorioRepository;
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
public class UpdateMaterialDeRelatorioService {

    private final MaterialDeRelatorioRepository materialDeRelatorioRepository;
    private final GetMaterialByIdAndRelatorioIdService getMaterialByIdAndRelatorioIdService;
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final CheckIfUserHasAccessToEditRelatorioService checkIfUserHasAccessToEditRelatorioService;
    private final GetRelatorioByIdExternoAndTenantIdService getRelatorioByIdExternoAndTenantIdService;
    private final GetObraByIdAndTenantIdService getObraByIdAndTenantIdService;

    public void execute(UpdateMaterialDeRelatorioRequest request, Long id, String relatorioExternalId, String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity userTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserHasAccessToEditRelatorioService.execute(userTenant);

        RelatorioEntity relatorio = getRelatorioByIdExternoAndTenantIdService.execute(relatorioExternalId, tenantId);

        checkIfConfiguracaoDeRelatorioDaObraPermiteMaterial(relatorio, tenantId);

        checkIfUserCanViewMateriais(userTenant);

        MaterialDeRelatorioEntity entity = getMaterialByIdAndRelatorioIdService.execute(id, relatorio.getId());
        entity.setDescricao(request.descricao());
        entity.setQuantidade(request.quantidade());
        entity.setTipoMaterial(request.tipoMaterial());

        materialDeRelatorioRepository.save(entity);
    }

    private void checkIfUserCanViewMateriais(UserTenantEntity userTenant) {
        if (userTenant.getAuthorities().getItensDeRelatorio().getMateriais()) return;

        throw new ForbiddenException();
    }

    private void checkIfConfiguracaoDeRelatorioDaObraPermiteMaterial(RelatorioEntity relatorio, Long tenantId) {
        ObraEntity obra = getObraByIdAndTenantIdService.execute(relatorio.getObraId(), tenantId);

        if (obra.getConfiguracaoRelatorios().getShowMateriais()) return;

        throw new ForbiddenException();
    }
}
