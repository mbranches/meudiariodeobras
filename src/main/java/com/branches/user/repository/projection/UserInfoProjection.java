package com.branches.user.repository.projection;

import com.branches.usertenant.domain.Authorities;
import com.branches.usertenant.domain.enums.PerfilUserTenant;

public interface UserInfoProjection {
    String getIdExterno();
    String getNome();
    String getEmail();
    String getCargo();
    String getFotoUrl();
    Authorities getAuthorities();
    PerfilUserTenant getPerfil();

}
