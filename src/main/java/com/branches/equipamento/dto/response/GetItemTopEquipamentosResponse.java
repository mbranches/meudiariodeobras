package com.branches.equipamento.dto.response;

import com.branches.equipamento.repository.projections.ItemTopEquipamentosProjection;

public record GetItemTopEquipamentosResponse(
        Long id,
        String descricao,
        Long quantidadeUso
) {
    public static GetItemTopEquipamentosResponse from(ItemTopEquipamentosProjection itemTopEquipamentosProjection) {
        return new GetItemTopEquipamentosResponse(
                itemTopEquipamentosProjection.getId(),
                itemTopEquipamentosProjection.getDescricao(),
                itemTopEquipamentosProjection.getQuantidadeUso()
        );
    }
}
