package com.branches.arquivo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateVideoDeRelatorioRequest(
        @NotBlank(message = "O campo 'base64Video' é obrigatório")
        String base64Video,
        @NotBlank(message = "O campo 'fileName' é obrigatório")
        String fileName,
        @NotNull(message = "O campo 'contentType' é obrigatório")
        String contentType
) {
}

