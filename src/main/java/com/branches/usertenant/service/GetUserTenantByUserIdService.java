package com.branches.usertenant.service;

import com.branches.exception.ForbiddenException;
import com.branches.obra.repository.ObraRepository;
import com.branches.obra.repository.projections.ObraResumeProjection;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.user.domain.UserEntity;
import com.branches.user.service.GetUserByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.domain.UserTenantKey;
import com.branches.usertenant.dto.response.UserTenantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetUserTenantByUserIdService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetUserByIdExternoService getUserByIdExternoService;
    private final GetUserTenantByIdService getUserTenantByIdService;
    private final ObraRepository obraRepository;

    public UserTenantResponse execute(String tenantExternalId, String userExternalId, List<UserTenantEntity> requestingUserTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(requestingUserTenants, tenantId);
        checkIfUserCanGetUser(currentUserTenant);

        UserEntity userToGet = getUserByIdExternoService.execute(userExternalId);

        UserTenantEntity userTenantToGet = getUserTenantByIdService.execute(UserTenantKey.from(userToGet.getId(), tenantId));

        List<ObraResumeProjection> obrasPermitidas = obraRepository.findResumedByIdInList(userTenantToGet.getObrasPermitidasIds());

        return UserTenantResponse.from(userTenantToGet, obrasPermitidas);
    }

    private void checkIfUserCanGetUser(UserTenantEntity currentUserTenant) {
        if (currentUserTenant.isAdministrador()) return;

        throw new ForbiddenException();
    }
}
