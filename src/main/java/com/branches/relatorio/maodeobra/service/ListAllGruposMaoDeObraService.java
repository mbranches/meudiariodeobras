package com.branches.relatorio.maodeobra.service;

import com.branches.exception.ForbiddenException;
import com.branches.relatorio.maodeobra.domain.GrupoMaoDeObraEntity;
import com.branches.relatorio.maodeobra.dto.response.GrupoMaoDeObraResponse;
import com.branches.relatorio.maodeobra.repository.GrupoMaoDeObraRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListAllGruposMaoDeObraService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GrupoMaoDeObraRepository grupoMaoDeObraRepository;

    public List<GrupoMaoDeObraResponse> execute(String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserHasAccessToGrupoMaoDeObra(currentUserTenant);

        List<GrupoMaoDeObraEntity> grupoMaoDeObraEntityList = grupoMaoDeObraRepository.findAllByTenantIdAndAtivoIsTrue(tenantId);

        return grupoMaoDeObraEntityList.stream()
                .map(GrupoMaoDeObraResponse::from)
                .toList();
    }

    private void checkIfUserHasAccessToGrupoMaoDeObra(UserTenantEntity currentUserTenant) {
        if (!currentUserTenant.getAuthorities().getCadastros().getMaoDeObra()) {
            throw new ForbiddenException();
        }
    }
}
