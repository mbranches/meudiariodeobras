package com.branches.relatorio.dto.response;

import com.branches.relatorio.repository.projections.RelatorioCountersProjection;
import org.springframework.data.domain.Page;

public record PageRelatorioResponse(
        int pageSize,
        int pageNumber,
        long totalElements,
        RelatorioListAndCountersResponse content,
        boolean isFirstPage,
        boolean isLastPage
) {
    public static PageRelatorioResponse from(Page<RelatorioResponse> page, RelatorioCountersProjection counters) {
        return new PageRelatorioResponse(
                page.getSize(),
                page.getNumber(),
                page.getTotalElements(),
                RelatorioListAndCountersResponse.from(page.getContent(), counters),
                page.isFirst(),
                page.isLast()
        );
    }
}
