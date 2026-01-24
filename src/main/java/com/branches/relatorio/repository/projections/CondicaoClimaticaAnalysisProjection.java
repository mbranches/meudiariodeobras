package com.branches.relatorio.repository.projections;

public interface CondicaoClimaticaAnalysisProjection {
    Long getTotal();
    Long getTotalPraticavel();
    Long getTotalImpraticavel();
    Long getTotalClaro();
    Long getTotalNublado();
    Long getTotalChuvoso();
}
