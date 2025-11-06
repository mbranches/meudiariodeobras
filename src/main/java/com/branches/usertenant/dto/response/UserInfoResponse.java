package com.branches.usertenant.dto.response;

import com.branches.tenant.domain.TenantEntity;
import com.branches.user.repository.projection.UserInfoProjection;
import com.branches.usertenant.domain.UserTenantAuthorities;
import com.branches.usertenant.domain.enums.PerfilUserTenant;

import java.util.List;

public record UserInfoResponse(
        String id,
        String nome,
        String email,
        String cargo,
        String fotoUrl,
        UserTenantAuthorities authorities,
        PerfilUserTenant perfil,
        List<TenantByUserInfoResponse> tenantsVinculados
) {
    public static UserInfoResponse from(UserInfoProjection user, List<TenantEntity> allUserTenants) {
        return new UserInfoResponse(
                user.getIdExterno(),
                user.getNome(),
                user.getEmail(),
                user.getCargo(),
                user.getFotoUrl(),
                user.getAuthorities(),
                user.getPerfil(),
                allUserTenants.stream()
                        .map(TenantByUserInfoResponse::from)
                        .toList()
        );
    }
}
