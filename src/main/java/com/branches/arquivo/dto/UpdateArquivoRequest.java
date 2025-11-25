package com.branches.arquivo.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateArquivoRequest(
        @NotBlank(message = "O campo 'descricao' é obrigatório")
        String descricao
) {
}
