package com.branches.condicaoclimatica.dto.response;

public record TotalizadoresByCondicaoClimaticaAnalysisResponse(
        Long total,
        Long totalPraticavel,
        Long totalImpraticavel,
        Long totalClaro,
        Long totalNublado,
        Long totalChuvoso
) {
}
