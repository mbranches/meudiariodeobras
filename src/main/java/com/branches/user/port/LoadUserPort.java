package com.branches.user.port;

import com.branches.user.domain.UserEntity;

public interface LoadUserPort {
    UserEntity getByIdExterno(String idExterno);

    UserEntity loadByEmail(String email);
}
