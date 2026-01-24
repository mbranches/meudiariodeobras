package com.branches.condicaoclimatica.dto.response;

import java.math.BigDecimal;

public record AnalysisCondicaoDoTempoResponse(
        BigDecimal porcentagemPraticavel,
        BigDecimal porcentagemNaoPraticavel
) {
}
