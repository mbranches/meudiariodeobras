package com.branches.usertenant.dto.response;

import com.branches.tenant.domain.TenantEntity;

public record TenantByUserInfoResponse(
    String id,
    String razaoSocial,
    String nomeFantasia
) {
    public static TenantByUserInfoResponse from(TenantEntity tenant) {
        return new TenantByUserInfoResponse(tenant.getIdExterno(), tenant.getRazaoSocial(), tenant.getNomeFantasia());
    }
}
