package com.branches.obra.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateConfigDeAssinaturaDeRelatorioDeObraRequest(
        @NotBlank(message = "O campo 'nomeAssinante' é obrigatório")
        String nomeAssinante
) {
}
