package com.branches.usertenant.dto.response;

import com.branches.tenant.repository.projection.TenantInfoProjection;

public record TenantInfoResponse(
        String id,
        String razaoSocial,
        String nome,
        String cnpj,
        String telefone,
        String logoUrl,
        ResponsavelInfoResponse responsavel,
        AssinaturaInfoResponse assinaturaAtiva,
        Long quantidadeDeUsersCriados,
        Long quantidadeDeObrasCriadas
) {
    public static TenantInfoResponse from(TenantInfoProjection tenant) {
        AssinaturaInfoResponse assinatura = tenant.getAssinaturaAtiva() != null ? com.branches.usertenant.dto.response.AssinaturaInfoResponse.from(tenant.getAssinaturaAtiva())
                : null;

        return new TenantInfoResponse(
                tenant.getIdExterno(),
                tenant.getRazaoSocial(),
                tenant.getNome(),
                tenant.getCnpj(),
                tenant.getTelefone(),
                tenant.getLogoUrl(),
                ResponsavelInfoResponse.from(tenant.getResponsavel()),
                assinatura,
                tenant.getQuantidadeDeUsersCriados(),
                tenant.getQuantidadeDeObrasCriadas()
        );
    }
}
