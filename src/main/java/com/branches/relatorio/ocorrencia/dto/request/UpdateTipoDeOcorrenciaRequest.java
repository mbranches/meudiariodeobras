package com.branches.relatorio.ocorrencia.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateTipoDeOcorrenciaRequest(
        @NotBlank(message = "O campo 'descricao' é obrigatório") String descricao
) {
}
