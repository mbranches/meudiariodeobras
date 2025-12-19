package com.branches.usertenant.dto.request;

import com.branches.usertenant.domain.Authorities;
import com.branches.usertenant.domain.enums.PerfilUserTenant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UpdateUserOfTenantRequest(
        @NotBlank(message = "O campo 'cargo' é obrigatório") String cargo,
        @NotNull(message = "O campo 'obrasPermitidasIds' é obrigatório") Set<String> obrasPermitidasIds,
        Authorities authorities,
        @NotNull(message = "O campo 'perfil' é obrigatório") PerfilUserTenant perfil,
        @NotNull(message = "O campo 'ativo' é obrigatório") Boolean ativo
) {
}
