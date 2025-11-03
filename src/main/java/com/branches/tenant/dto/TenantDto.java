package com.branches.tenant.dto;

import com.branches.tenant.domain.TenantEntity;

public record TenantDto(
        Long id,
        String idExterno,
        String razaoSocial,
        String nomeFantasia,
        String cnpj,
        String logoUrl,
        String telefone,
        Boolean ativo
) {
    public static TenantDto from(TenantEntity tenant) {
        return new TenantDto(
                tenant.getId(),
                tenant.getIdExterno(),
                tenant.getRazaoSocial(),
                tenant.getNomeFantasia(),
                tenant.getCnpj(),
                tenant.getLogoUrl(),
                tenant.getTelefone(),
                tenant.getAtivo()
        );
    }
}
