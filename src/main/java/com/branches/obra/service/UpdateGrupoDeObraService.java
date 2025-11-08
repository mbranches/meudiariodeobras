package com.branches.obra.service;

import com.branches.exception.ForbiddenException;
import com.branches.obra.domain.GrupoDeObraEntity;
import com.branches.obra.dto.request.UpdateGrupoDeObraRequest;
import com.branches.obra.repository.GrupoDeObraRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateGrupoDeObraService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetGrupoDeObraByIdAndTenantIdService getGrupoDeObraByIdAndTenantIdService;
    private final GrupoDeObraRepository grupoDeObraRepository;

    public void execute(Long id, String tenantExternalId, UpdateGrupoDeObraRequest request, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserCanUpdateGrupoDeObra(currentUserTenant);

        GrupoDeObraEntity grupoDeObraEntity = getGrupoDeObraByIdAndTenantIdService.execute(id, tenantId);

        grupoDeObraEntity.setDescricao(request.descricao());

        grupoDeObraRepository.save(grupoDeObraEntity);
    }

    private void checkIfUserCanUpdateGrupoDeObra(UserTenantEntity currentUserTenant) {
        if (!currentUserTenant.getAuthorities().getCadastros().getCanCreateAndEditGrupoDeObras()) {
            throw new ForbiddenException();
        }
    }
}
