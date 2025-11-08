package com.branches.obra.service;

import com.branches.exception.ForbiddenException;
import com.branches.obra.controller.CreateGrupoDeObraRequest;
import com.branches.obra.domain.GrupoDeObraEntity;
import com.branches.obra.dto.response.CreateGrupoDeObraResponse;
import com.branches.obra.repository.GrupoDeObraRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateGrupoDeObraService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GrupoDeObraRepository grupoDeObraRepository;

    public CreateGrupoDeObraResponse execute(String tenantExternalId, CreateGrupoDeObraRequest request, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserCanCreateGrupoDeObra(currentUserTenant);

        GrupoDeObraEntity grupoDeObraEntity = GrupoDeObraEntity.builder()
                .tenantId(tenantId)
                .descricao(request.descricao())
                .ativo(true)
                .build();

        GrupoDeObraEntity saved = grupoDeObraRepository.save(grupoDeObraEntity);

        return CreateGrupoDeObraResponse.from(saved);
    }

    private void checkIfUserCanCreateGrupoDeObra(UserTenantEntity currentUserTenant) {
        if (!currentUserTenant.getAuthorities().getCadastros().getGrupoDeObras()) {
            throw new ForbiddenException();
        }
    }
}
