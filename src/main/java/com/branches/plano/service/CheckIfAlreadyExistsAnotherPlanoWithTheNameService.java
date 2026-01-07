package com.branches.plano.service;

import com.branches.exception.BadRequestException;
import com.branches.plano.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfAlreadyExistsAnotherPlanoWithTheNameService {
    private final PlanoRepository planoRepository;

    public void execute(String name) {
        boolean exists = planoRepository.existsByNome(name);

        if (!exists) return;

        throw new BadRequestException("Já existe um plano com o nome '%s'".formatted(name));
    }
}
