package com.branches.relatorio.dto.response;

import com.branches.relatorio.repository.projections.RelatorioCountersProjection;

import java.util.List;

public record RelatorioListAndCountersResponse(
        List<RelatorioResponse> relatorios,
        RelatorioCountersResponse contadores
) {
    public static RelatorioListAndCountersResponse from(List<RelatorioResponse> content, RelatorioCountersProjection counters) {
        return new RelatorioListAndCountersResponse(content, RelatorioCountersResponse.from(counters));
    }
}
