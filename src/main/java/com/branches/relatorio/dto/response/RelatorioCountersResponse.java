package com.branches.relatorio.dto.response;

import com.branches.relatorio.repository.projections.RelatorioCountersProjection;

public record RelatorioCountersResponse(
        Long total,
        Long totalEmAndamento,
        Long totalEmRevisao,
        Long totalAprovados
) {
    public static RelatorioCountersResponse from(RelatorioCountersProjection counters) {
        return new RelatorioCountersResponse(
                counters.getTotal(),
                counters.getTotalEmAndamento(),
                counters.getTotalEmRevisao(),
                counters.getTotalAprovados()
        );
    }
}
