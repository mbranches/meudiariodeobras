package com.branches.condicaoclimatica.dto.response;

import com.branches.relatorio.repository.projections.CondicaoClimaticaAnalysisProjection;

import java.math.BigDecimal;

public record CondicaoClimaticaAnalisysResponse(
        TotalizadoresByCondicaoClimaticaAnalysisResponse totalizadores,
        AnalysisCondicaoDoTempoResponse condicaoDoTempo,
        AnalysisCondicaoClimaticaResponse condicaoClimatica
) {
    public static CondicaoClimaticaAnalisysResponse from(CondicaoClimaticaAnalysisProjection analysis, BigDecimal porcentagemPraticavel, BigDecimal porcentagemNaoPraticavel, BigDecimal porcentagemChuva, BigDecimal porcentagemNublado, BigDecimal porcentagemClaro) {
        var totalizadores = new TotalizadoresByCondicaoClimaticaAnalysisResponse(
                analysis.getTotal(),
                analysis.getTotalPraticavel(),
                analysis.getTotalImpraticavel(),
                analysis.getTotalClaro(),
                analysis.getTotalNublado(),
                analysis.getTotalChuvoso()
        );

        var condicaoDoTempo = new AnalysisCondicaoDoTempoResponse(
                porcentagemPraticavel,
                porcentagemNaoPraticavel
        );

        var condicaoClimatica = new AnalysisCondicaoClimaticaResponse(
                porcentagemClaro,
                porcentagemNublado,
                porcentagemChuva
        );

        return new CondicaoClimaticaAnalisysResponse(
                totalizadores,
                condicaoDoTempo,
                condicaoClimatica
        );
    }
}
