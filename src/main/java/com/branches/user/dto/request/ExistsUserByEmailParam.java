package com.branches.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ExistsUserByEmailParam(
        @NotBlank(message = "O campo 'email' é obrigatório") String email
) {
}
