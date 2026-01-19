package com.branches.tenant.dto.request;

import com.branches.tenant.domain.enums.SegmentoTenant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTenantRequest(
        @NotBlank(message = "O campo 'razaoSocial' é obrigatório")
        String razaoSocial,
        @NotBlank(message = "O campo 'nome' é obrigatório")
        String nome,
        @NotBlank(message = "O campo 'cnpj' é obrigatório")
        String cnpj,
        @NotBlank(message = "O campo 'telefone' é obrigatório")
        String telefone,
        @NotBlank(message = "O campo 'cargoResponsavel' é obrigatório")
        String cargoResponsavel,
        @NotNull(message = "O campo 'segmento' é obrigatório")
        SegmentoTenant segmento
) {
}
