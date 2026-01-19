package com.branches.tenant.dto.response;

import com.branches.tenant.domain.TenantEntity;

public record CreateTenantResponse(
        String id,
        String razaoSocial,
        String nome,
        String cnpj,
        String telefone,
        String segmento
) {
    public static CreateTenantResponse from(TenantEntity saved) {
        return new CreateTenantResponse(
                saved.getIdExterno(),
                saved.getRazaoSocial(),
                saved.getNome(),
                saved.getCnpj(),
                saved.getTelefone(),
                saved.getSegmento().name()
        );
    }
}
