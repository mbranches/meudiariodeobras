package com.branches.condicaoclimatica.dto.response;

import java.math.BigDecimal;

public record AnalysisCondicaoClimaticaResponse(
        BigDecimal porcentagemClaro,
        BigDecimal porcentagemNublado,
        BigDecimal porcentagemChuvoso
) {
}
