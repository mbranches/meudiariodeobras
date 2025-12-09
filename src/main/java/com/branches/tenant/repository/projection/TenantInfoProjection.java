package com.branches.tenant.repository.projection;

import com.branches.assinatura.domain.AssinaturaEntity;

public interface TenantInfoProjection {
    String getIdExterno();
    String getRazaoSocial();
    String getNome();
    String getCpfCnpj();
    String getTelefone();
    String getLogoUrl();
    String getNomeUsuarioResponsavel();
    AssinaturaEntity getAssinaturaAtiva();
    Long getQuantidadeDeUsersCriados();
    Long getQuantidadeDeObrasCriadas();
}
