package com.branches.relatorio.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AssinarRelatorioRequest(
        @NotBlank(message = "O campo 'base64Signature' é obrigatório")
        String base64Signature
) {
}
