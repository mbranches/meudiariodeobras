package com.branches.relatorio.equipamento.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateEquipamentoRequest(
        @NotBlank(message = "O campo 'descricao' é obrigatório") String descricao) {
}