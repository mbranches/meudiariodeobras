package com.branches.relatorio.rdo.repository.projections;

import com.branches.relatorio.rdo.domain.CaracteristicaDePeriodoDoDiaEntity;
import com.branches.relatorio.rdo.domain.enums.StatusRelatorio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface RelatorioDetailsProjection {
    Long getId();
    String getIdExterno();
    String getTenantLogoUrl();
    String getObraIdExterno();
    String getObraNome();
    String getObraEndereco();
    String getObraContratante();
    String getObraResponsavel();
    LocalDate getData();
    String getNumero();
    Long getPrazoContratual();
    Long getPrazoDecorrido();
    Long getPrazoPraVencer();
    CaracteristicaDePeriodoDoDiaEntity getCaracteristicaManha();
    CaracteristicaDePeriodoDoDiaEntity getCaracteristicaTarde();
    CaracteristicaDePeriodoDoDiaEntity getCaracteristicaNoite();
    BigDecimal getIndicePluviometrico();
    StatusRelatorio getStatus();
    String getCriadoPor();
    LocalDateTime getCriadoEm();
    String getUltimaModificacaoPor();
    LocalDateTime getUltimaModificacaoEm();
}
