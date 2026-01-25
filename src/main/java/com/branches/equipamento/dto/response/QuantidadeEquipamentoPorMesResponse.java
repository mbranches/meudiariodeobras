package com.branches.equipamento.dto.response;

import com.branches.equipamento.repository.projections.QuantidadeEquipamentoPorMesProjection;
import com.branches.shared.dto.response.TotalPorMesResponse;

import java.util.List;

public record QuantidadeEquipamentoPorMesResponse(
        Long id,
        String descricao,
        List<TotalPorMesResponse> totalPorMes
) {
    public static QuantidadeEquipamentoPorMesResponse from(List<QuantidadeEquipamentoPorMesProjection> projections) {
        if (projections == null || projections.isEmpty()) {
            return null;
        }

        Long equipamentoId = projections.getFirst().getEquipamentoId();
        String equipamentoDescricao = projections.getFirst().getEquipamentoDescricao();

        List<TotalPorMesResponse> totalPorMesList = projections.stream()
                .map(proj -> new TotalPorMesResponse(
                        proj.getMes(),
                        proj.getQuantidade()
                ))
                .toList();

        return new QuantidadeEquipamentoPorMesResponse(
                equipamentoId,
                equipamentoDescricao,
                totalPorMesList
        );
    }
}
