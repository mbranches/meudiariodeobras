package com.branches.tenant.service;

import com.branches.exception.BadRequestException;
import com.branches.tenant.repository.TenantRepository;
import com.branches.utils.CnpjFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfDoesntExistsTenantWithTheCnpjService {
    private final TenantRepository tenantRepository;
    private final CnpjFormatter cnpjFormatter;

    public void execute(String cnpj) {
        boolean exists = tenantRepository.existsByCnpj(cnpj);

        if (!exists) return;

        String formattedCnpj = cnpjFormatter.execute(cnpj);

        throw new BadRequestException("O cnpj '%s' já está em uso".formatted(formattedCnpj));
    }
}
