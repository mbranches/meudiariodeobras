package com.branches.user.adapter;

import com.branches.user.domain.UserEntity;
import com.branches.shared.exception.NotFoundException;
import com.branches.user.repository.UserRepository;
import com.branches.user.port.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserPersistenceAdapter implements LoadUserPort {
    private final UserRepository userRepository;

    @Override
    public UserEntity getByIdExterno(String idExterno) {
        return userRepository.findByIdExterno(idExterno)
                .orElseThrow(() -> new NotFoundException("User não encontrado com idExterno: " + idExterno));
    }

    @Override
    public UserEntity loadByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User não encontrado com email: " + email));
    }
}
