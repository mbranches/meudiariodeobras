package com.branches.usertenant.dto.request;

import com.branches.usertenant.domain.Authorities;
import com.branches.usertenant.domain.enums.PerfilUserTenant;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UpdateUserOfTenantRequest(
        @NotNull(message = "O campo 'obrasIds' é obrigatório") Set<String> obrasIds,
        Authorities authorities,
        @NotNull(message = "O campo 'perfil' é obrigatório") PerfilUserTenant perfil,
        @NotNull(message = "O campo 'ativo' é obrigatório") Boolean ativo
) {
}
