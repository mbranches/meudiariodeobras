package com.branches.tenant.service;

import com.branches.exception.BadRequestException;
import com.branches.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfDoesntExistsTenantWithTheCnpjService {
    private final TenantRepository tenantRepository;

    public void execute(String cnpj) {
        boolean exists = tenantRepository.existsByCnpj(cnpj);

        if (!exists) return;

        throw new BadRequestException("O cnpj '%s' já está em uso".formatted(cnpj));
    }
}
