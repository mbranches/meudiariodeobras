package com.branches.relatorio.equipamento.dto.response;

import com.branches.relatorio.equipamento.domain.EquipamentoEntity;

public record CreateEquipamentoResponse(
        Long id,
        String descricao
) {
    public static CreateEquipamentoResponse from(EquipamentoEntity saved) {
        return new CreateEquipamentoResponse(
                saved.getId(),
                saved.getDescricao()
        );
    }
}
