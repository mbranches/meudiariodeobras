package com.branches.user.service;

import com.branches.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExistsUserByEmailService {
    private final UserRepository userRepository;

    public boolean execute(String email) {
        return userRepository.existsByEmail(email);
    }
}
