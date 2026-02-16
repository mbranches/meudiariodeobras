package com.branches.suporte.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FormularioContatoRequest(
        @NotBlank(message = "O campo 'nome' é obrigatório.")
        String nome,
        @NotBlank(message = "O campo 'email' é obrigatório.")
        String email,
        String telefone,
        String Empresa,
        String mensagem
) {
}
