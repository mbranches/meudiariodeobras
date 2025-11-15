package com.branches.user.service;

import com.branches.user.domain.UserEntity;
import com.branches.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FindUserByEmailService {
    private final UserRepository userRepository;

    public Optional<UserEntity> execute(String email) {
        return userRepository.findByEmail(email);
    }
}
