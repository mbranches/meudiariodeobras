package com.branches.equipamento.dto.request;

import jakarta.validation.constraints.Min;

public record UpdateEquipamentoDeRelatorioRequest(
        @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
        Integer quantidade
) {
}
