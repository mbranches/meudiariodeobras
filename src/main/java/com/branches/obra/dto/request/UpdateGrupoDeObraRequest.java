package com.branches.obra.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateGrupoDeObraRequest(
        @NotBlank(message = "O campo 'descricao' é obrigatório") String descricao
) {
}
