package com.branches.tenant.port;

import com.branches.tenant.domain.TenantEntity;

public interface LoadTenantPort {
    TenantEntity getById(Long id);

    TenantEntity getByIdExterno(String idExterno);
}
