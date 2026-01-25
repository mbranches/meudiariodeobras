package com.branches.equipamento.dto.response;

import com.branches.shared.dto.response.TotalPorMesResponse;

import java.util.List;

public record AnaliseDeEquipamentosPorMesResponse(
        List<TotalPorMesResponse> totalPorMes,
        List<QuantidadeEquipamentoPorMesResponse> quantidadeDeEquipamentoPorMes
) {
}
