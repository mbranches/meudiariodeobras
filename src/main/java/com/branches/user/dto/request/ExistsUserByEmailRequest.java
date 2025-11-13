package com.branches.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ExistsUserByEmailRequest(
        @NotBlank(message = "O campo 'email' é obrigatório") String email
) {
}
