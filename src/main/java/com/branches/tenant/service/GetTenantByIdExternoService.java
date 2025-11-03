package com.branches.tenant.service;

import com.branches.tenant.dto.TenantDto;
import com.branches.tenant.port.LoadTenantPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTenantByIdExternoService {
    private final LoadTenantPort loadTenant;

    public TenantDto execute(String idExterno) {
        return TenantDto.from(loadTenant.getByIdExterno(idExterno));
    }
}
