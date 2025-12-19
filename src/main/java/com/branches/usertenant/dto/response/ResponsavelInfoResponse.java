package com.branches.usertenant.dto.response;

import com.branches.user.domain.UserEntity;

public record ResponsavelInfoResponse(
        String id,
        String nome,
        String email
) {
    public static ResponsavelInfoResponse from(UserEntity user) {
        return new ResponsavelInfoResponse(
                user.getIdExterno(),
                user.getNome(),
                user.getEmail()
        );
    }
}
