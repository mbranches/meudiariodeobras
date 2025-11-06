package com.branches.tenant.service;

import com.branches.exception.NotFoundException;
import com.branches.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTenantIdByIdExternoService {
    private final TenantRepository tenantRepository;

    public Long execute(String idExterno) {
        return tenantRepository.findTenantIdByIdExternoAndAtivoIsTrue(idExterno)
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com o idExterno: " + idExterno));
    }
}
