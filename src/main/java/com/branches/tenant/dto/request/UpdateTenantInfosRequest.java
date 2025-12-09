package com.branches.tenant.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateTenantInfosRequest(
        @NotBlank(message = "O campo 'nome' é obrigatório")
        String nome,
        @NotBlank(message = "O campo 'telefone' é obrigatório")
        String telefone
) {
}
