package com.branches.usertenant.dto.request;

import com.branches.usertenant.domain.Authorities;
import com.branches.usertenant.domain.enums.PerfilUserTenant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AddUserToTenantRequest(
        @NotBlank(message = "O campo 'email' é obrigatório")
        @Email(message = "O campo 'email' deve ser um e-mail válido")
        String email,
        String nome,
        String password,
        String cargo,
        Authorities authorities,
        @NotNull(message = "O campo 'perfil' é obrigatório")
        PerfilUserTenant perfil,
        @NotNull(message = "O campo 'obrasPermitidasIds' é obrigatório")
        Set<String> obrasPermitidasIds
) {
}
