package com.branches.usertenant.service;

import com.branches.exception.NotFoundException;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.repository.UserTenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUserTenantByUserIdExternoAndTenantIdService {
    private final UserTenantRepository userTenantRepository;

    public UserTenantEntity execute(String userIdExterno, Long tenantId) {
        return userTenantRepository.findByUserIdExternoAndTenantId(userIdExterno, tenantId)
                .orElseThrow(() -> new NotFoundException("User não encontrado para o tenant"));
    }
}
