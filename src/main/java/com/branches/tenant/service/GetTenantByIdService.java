package com.branches.tenant.service;

import com.branches.exception.NotFoundException;
import com.branches.tenant.domain.TenantEntity;
import com.branches.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTenantByIdService {
    private final TenantRepository tenantRepository;

    public TenantEntity execute(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com o id: " + tenantId));
    }
}
