package com.branches.user.service;

import com.branches.shared.dto.UserDto;
import com.branches.user.port.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUserByIdExternoService {
    private final LoadUserPort loadUser;

    public UserDto execute(String idExterno) {
        return UserDto.of(loadUser.getByIdExterno(idExterno));
    }
}
