package com.branches.tenant.repository.projection;

import com.branches.assinaturadeplano.domain.AssinaturaDePlanoEntity;
import com.branches.plano.domain.PeriodoTesteEntity;
import com.branches.user.domain.UserEntity;

public interface TenantInfoProjection {
    String getIdExterno();
    String getRazaoSocial();
    String getNome();
    String getCnpj();
    String getTelefone();
    String getLogoUrl();
    UserEntity getResponsavel();
    AssinaturaDePlanoEntity getAssinaturaCorrente();
    Long getQuantidadeDeUsersCriados();
    Long getQuantidadeDeObrasCriadas();
    Long getQuantidadeDeRelatoriosCriados();
    PeriodoTesteEntity getPeriodoDeTeste();
    Boolean getAlreadyHadSubscription();
}
