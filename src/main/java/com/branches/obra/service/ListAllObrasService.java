package com.branches.obra.service;

import com.branches.exception.ForbiddenException;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.dto.response.ObraByListAllResponse;
import com.branches.obra.repository.ObraRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.domain.enums.PerfilUserTenant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.branches.usertenant.domain.enums.PerfilUserTenant.ADMINISTRADOR;

@Service
@RequiredArgsConstructor
public class ListAllObrasService {
    private final ObraRepository obraRepository;
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;

    public List<ObraByListAllResponse> execute(String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenant(userTenants, tenantId);

        PerfilUserTenant userPerfil = currentUserTenant.getPerfil();
        List<Long> userAllowedObrasIds = currentUserTenant.getObrasPermitidasIds();

        List<ObraEntity> obras = userPerfil.equals(ADMINISTRADOR) ? obraRepository.findAllByTenantId(tenantId)
                : obraRepository.findAllByTenantIdAndIdIn(tenantId, userAllowedObrasIds);

        return obras.stream()
                .map(ObraByListAllResponse::from)
                .toList();
    }

    private UserTenantEntity getCurrentUserTenant(List<UserTenantEntity> userTenants, Long tenantId) {
        return userTenants.stream()
                .filter(ut -> ut.getTenantId().equals(tenantId))
                .findFirst()
                .orElseThrow(ForbiddenException::new);
    }
}
