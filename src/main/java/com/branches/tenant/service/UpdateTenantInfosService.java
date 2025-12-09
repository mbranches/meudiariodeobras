package com.branches.tenant.service;

import com.branches.exception.ForbiddenException;
import com.branches.tenant.domain.TenantEntity;
import com.branches.tenant.repository.TenantRepository;
import com.branches.tenant.dto.request.UpdateTenantInfosRequest;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import com.branches.utils.ValidatePhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateTenantInfosService {
    private final TenantRepository tenantRepository;
    private final GetTenantByIdExternoService getTenantByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final ValidatePhoneNumber validatePhoneNumber;

    public void execute(String tenantExternalId, List<UserTenantEntity> userTenantEntities, UpdateTenantInfosRequest request) {
        TenantEntity tenantEntity = getTenantByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenantEntities, tenantEntity.getId());
        checkIfUserCanEditTenantInfos(currentUserTenant);

        validatePhoneNumber.execute(request.telefone());

        tenantEntity.setTelefone(request.telefone());
        tenantEntity.setNome(request.nome());

        tenantRepository.save(tenantEntity);
    }

    private void checkIfUserCanEditTenantInfos(UserTenantEntity currentUserTenant) {
        if (currentUserTenant.isAdministrador()) return;

        throw new ForbiddenException();
    }
}
