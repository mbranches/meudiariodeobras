package com.branches.maodeobra.dto.response;

import com.branches.maodeobra.repository.projections.AnaliseMaoDeObraPorMesProjection;
import com.branches.shared.dto.response.TotalDecimalPorMesResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record AnaliseMaoDeObraPorMesResponse(
        Long id,
        String nome,
        String funcao,
        List<TotalDecimalPorMesResponse> totalMaoDeObraPorMes,
        Long totalFaltas,
        Long totalPresencas
) {
    public static AnaliseMaoDeObraPorMesResponse from(List<AnaliseMaoDeObraPorMesProjection> analiseMaoDeObraPorMesProjections) {
        if (analiseMaoDeObraPorMesProjections == null || analiseMaoDeObraPorMesProjections.isEmpty()) {
            return null;
        }

        AnaliseMaoDeObraPorMesProjection firstProjection = analiseMaoDeObraPorMesProjections.getFirst();

        List<TotalDecimalPorMesResponse> totalMaoDeObraPorMes = analiseMaoDeObraPorMesProjections.stream()
                .map(proj -> new TotalDecimalPorMesResponse(
                        proj.getMes(),
                        BigDecimal.valueOf(proj.getTotalMinutos()).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP)
                ))
                .toList();

        return new AnaliseMaoDeObraPorMesResponse(
                firstProjection.getMaoDeObraId(),
                firstProjection.getNomeMaoDeObra(),
                firstProjection.getFuncaoMaoDeObra(),
                totalMaoDeObraPorMes,
                firstProjection.getTotalFaltas(),
                firstProjection.getTotalPresentes()
        );
    }
}
