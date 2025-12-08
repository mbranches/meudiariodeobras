package com.branches.user.service;

import com.branches.exception.BadRequestException;
import com.branches.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfDoesntExistsUserWithTheEmailService {
    private final UserRepository userRepository;

    public void execute(String email) {
        boolean exists = userRepository.existsByEmail(email);

        if (!exists) return;

        throw new BadRequestException("O email '%s' já está em uso".formatted(email));
    }
}
