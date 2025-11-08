package com.branches.relatorio.tipodeocorrencia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTipoDeOcorrenciaRequest(
        @NotBlank(message = "O campo 'descricao' é obrigatório") String descricao) {
}