package com.branches.tenant.adapter;

import com.branches.shared.exception.NotFoundException;
import com.branches.tenant.domain.TenantEntity;
import com.branches.tenant.port.LoadTenantPort;
import com.branches.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TenantPersistenceAdapter implements LoadTenantPort {
    private final TenantRepository tenantRepository;

    @Override
    public TenantEntity getById(Long id) {
        return tenantRepository.findByIdAndAtivoIsTrue(id)
                .orElseThrow(() -> new NotFoundException("Tenant não encontado com id: " + id));
    }

    @Override
    public TenantEntity getByIdExterno(String idExterno) {
        return tenantRepository.findByIdExternoAndAtivoIsTrue(idExterno)
                .orElseThrow(() -> new NotFoundException("Tenant não encontado com id externo: " + idExterno));
    }
}
