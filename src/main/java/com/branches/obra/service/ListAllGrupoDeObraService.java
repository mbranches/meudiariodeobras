package com.branches.obra.service;

import com.branches.exception.ForbiddenException;
import com.branches.obra.domain.GrupoDeObraEntity;
import com.branches.obra.dto.response.GrupoDeObraResponse;
import com.branches.obra.repository.GrupoDeObraRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListAllGrupoDeObraService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GrupoDeObraRepository grupoDeObraRepository;

    public List<GrupoDeObraResponse> execute(String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserHasAccessToGrupoDeObra(currentUserTenant);

        List<GrupoDeObraEntity> grupoDeObraEntityList = grupoDeObraRepository.findAllByTenantIdAndAtivoIsTrue(tenantId);

        return grupoDeObraEntityList.stream()
                .map(GrupoDeObraResponse::from)
                .toList();
    }

    private void checkIfUserHasAccessToGrupoDeObra(UserTenantEntity currentUserTenant) {
        if (!currentUserTenant.getAuthorities().getCadastros().getGrupoDeObras()) {
            throw new ForbiddenException();
        }
    }
}
