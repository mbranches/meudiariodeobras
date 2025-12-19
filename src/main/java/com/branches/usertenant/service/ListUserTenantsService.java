package com.branches.usertenant.service;

import com.branches.obra.repository.ObraRepository;
import com.branches.obra.repository.projections.ObraResumeProjection;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.dto.response.UserTenantResponse;
import com.branches.usertenant.repository.UserTenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListUserTenantsService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final UserTenantRepository userTenantRepository;
    private final ObraRepository obraRepository;

    public List<UserTenantResponse> execute(String tenantExternalId, List<UserTenantEntity> requestingUserTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        getCurrentUserTenantService.execute(requestingUserTenants, tenantId);

        List<UserTenantEntity> userTenants = userTenantRepository.findAllByTenantId(tenantId);
        List<Long> todasAsObrasPermitidasIds = userTenants.stream().flatMap(ut -> ut.getObrasPermitidasIds().stream()).distinct().toList();

        List<ObraResumeProjection> obras = obraRepository.findResumedByIdInList(todasAsObrasPermitidasIds);

        Map<UserTenantEntity, List<ObraResumeProjection>> mapUserTenantAndObras = userTenants.stream()
                .collect(
                        Collectors.toMap(
                                ut -> ut,
                                ut -> obras.stream()
                                        .filter(obra -> ut.getObrasPermitidasIds().contains(obra.getId())).toList()
                        )
                );

        return mapUserTenantAndObras.entrySet().stream()
                .map(entry -> UserTenantResponse.from(entry.getKey(), entry.getValue()))
                .toList();
    }
}
