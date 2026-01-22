package com.branches.relatorio.repository.projections;

public interface RelatorioCountersProjection {
    Long getTotal();
    Long getTotalEmAndamento();
    Long getTotalEmRevisao();
    Long getTotalAprovados();
}
