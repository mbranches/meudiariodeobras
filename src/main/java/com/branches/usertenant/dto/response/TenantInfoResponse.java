package com.branches.usertenant.dto.response;

import com.branches.tenant.repository.projection.TenantInfoProjection;

public record TenantInfoResponse(
        String id,
        String razonSocial,
        String nomeFantasia,
        String cnpj,
        String telefone,
        String logoUrl,
        String responsavelNome,
        AssinaturaInfoResponse assinaturaAtiva,
        Long quantidadeDeUsersCriados,
        Long quantidadeDeObrasCriadas
) {
    public static TenantInfoResponse from(TenantInfoProjection tenant) {
        AssinaturaInfoResponse assinatura = tenant.getAssinaturaAtiva() != null ? AssinaturaInfoResponse.from(tenant.getAssinaturaAtiva())
                : null;

        return new TenantInfoResponse(
                tenant.getIdExterno(),
                tenant.getRazaoSocial(),
                tenant.getNomeFantasia(),
                tenant.getCnpj(),
                tenant.getTelefone(),
                tenant.getLogoUrl(),
                tenant.getNomeUsuarioResponsavel(),
                assinatura,
                tenant.getQuantidadeDeUsersCriados(),
                tenant.getQuantidadeDeObrasCriadas()
        );
    }
}
