package com.branches.maodeobra.dto.response;

import com.branches.shared.dto.response.TotalDecimalPorMesResponse;
import com.branches.shared.dto.response.TotalPorMesResponse;

import java.util.List;

public record AnaliseDeMaoDeObraPorMesResponse(List<TotalDecimalPorMesResponse> totalHorasMaoDeObraPorMes,
                                               List<AnaliseMaoDeObraPorMesResponse> analiseMaoDeObraPorMes) {
}
