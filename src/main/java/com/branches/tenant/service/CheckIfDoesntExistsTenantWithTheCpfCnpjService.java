package com.branches.tenant.service;

import com.branches.exception.BadRequestException;
import com.branches.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfDoesntExistsTenantWithTheCpfCnpjService {
    private final TenantRepository tenantRepository;

    public void execute(String cpfCnpj) {
        boolean exists = tenantRepository.existsByCpfCnpj(cpfCnpj);

        if (!exists) return;

        throw new BadRequestException("O cpf ou cnpj '%s' já está em uso".formatted(cpfCnpj));
    }
}
