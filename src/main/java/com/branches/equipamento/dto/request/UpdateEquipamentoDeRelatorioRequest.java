package com.branches.equipamento.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateEquipamentoDeRelatorioRequest(
        @NotNull(message = "O campo 'equipamentoId' é obrigatório")
        Long equipamentoId,
        @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
        Integer quantidade
) {
}
