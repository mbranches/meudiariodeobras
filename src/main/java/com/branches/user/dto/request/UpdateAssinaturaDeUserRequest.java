package com.branches.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateAssinaturaDeUserRequest(
        @NotBlank(message = "O campo 'base64Assinatura' é obrigatório")
        String base64Assinatura
) {
}
