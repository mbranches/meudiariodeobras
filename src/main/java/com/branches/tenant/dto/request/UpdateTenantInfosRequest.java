package com.branches.tenant.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateTenantInfosRequest(
        @NotBlank(message = "O campo 'nomeFantasia' é obrigatório")
        String nomeFantasia,
        @NotBlank(message = "O campo 'telefone' é obrigatório")
        String telefone
) {
}
