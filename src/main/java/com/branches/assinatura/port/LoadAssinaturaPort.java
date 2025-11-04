package com.branches.assinatura.port;

import com.branches.assinatura.domain.AssinaturaEntity;

public interface LoadAssinaturaPort {
    AssinaturaEntity getAssinaturaAtivaByTenantId(Long tenantId);
}
