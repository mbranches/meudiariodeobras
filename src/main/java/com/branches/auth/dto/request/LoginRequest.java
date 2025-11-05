package com.branches.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "O campo 'email' é obrigatório") String email,
        @NotBlank(message = "O campo 'password' é obrigatório") String password
) {
}
