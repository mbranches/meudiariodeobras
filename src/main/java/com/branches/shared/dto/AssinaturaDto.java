package com.branches.shared.dto;

import com.branches.assinatura.domain.AssinaturaEntity;

public record AssinaturaDto(
        Long id,
        Long tenantId,
        Long planoId
) {
    public static AssinaturaDto from(AssinaturaEntity assinatura) {
        return new AssinaturaDto(
                assinatura.getId(),
                assinatura.getTenantId(),
                assinatura.getPlanoId()
        );
    }
}
