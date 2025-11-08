package com.branches.relatorio.maodeobra.service;

import com.branches.exception.ForbiddenException;
import com.branches.relatorio.maodeobra.domain.GrupoMaoDeObraEntity;
import com.branches.relatorio.maodeobra.repository.GrupoMaoDeObraRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeleteGrupoMaoDeObraService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetGrupoMaoDeObraByIdAndTenantIdService getGrupoMaoDeObraByIdAndTenantIdService;
    private final GrupoMaoDeObraRepository grupoMaoDeObraRepository;

    public void execute(Long id, String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserHasAccessToGrupoMaoDeObra(currentUserTenant);

        GrupoMaoDeObraEntity grupoMaoDeObraEntity = getGrupoMaoDeObraByIdAndTenantIdService.execute(id, tenantId);

        grupoMaoDeObraEntity.setAtivo(false);

        grupoMaoDeObraRepository.save(grupoMaoDeObraEntity);
    }

    private void checkIfUserHasAccessToGrupoMaoDeObra(UserTenantEntity currentUserTenant) {
        if (!currentUserTenant.getAuthorities().getCadastros().getMaoDeObra()) {
            throw new ForbiddenException();
        }
    }
}

