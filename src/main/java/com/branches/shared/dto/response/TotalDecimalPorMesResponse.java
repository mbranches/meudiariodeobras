package com.branches.shared.dto.response;

import java.math.BigDecimal;

public record TotalDecimalPorMesResponse(
        Integer mes,
        BigDecimal total
) {
}
