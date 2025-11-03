package com.branches.shared.dto;

import com.branches.user.domain.UserEntity;
import com.branches.user.domain.enums.Role;

public record UserDto(
        Long id,
        String idExterno,
        String nome,
        String email,
        String password,
        String cargo,
        Role role,
        String fotoUrl,
        Boolean ativo
) {
    public static UserDto of(UserEntity user) {
        return new UserDto(
                user.getId(),
                user.getIdExterno(),
                user.getNome(),
                user.getPassword(),
                user.getEmail(),
                user.getCargo(),
                user.getRole(),
                user.getFotoUrl(),
                user.getAtivo()
        );
    }
}