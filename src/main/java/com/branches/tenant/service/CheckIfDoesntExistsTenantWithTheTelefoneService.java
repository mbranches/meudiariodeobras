package com.branches.tenant.service;

import com.branches.exception.BadRequestException;
import com.branches.tenant.repository.TenantRepository;
import com.branches.utils.TelefoneFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfDoesntExistsTenantWithTheTelefoneService {
    private final TenantRepository tenantRepository;
    private final TelefoneFormatter telefoneFormatter;

    public void execute(String telefone) {
        boolean exists = tenantRepository.existsByTelefone(telefone);

        if (!exists) return;

        String formattedTelefone = telefoneFormatter.execute(telefone);

        throw new BadRequestException("O telefone '%s' já está em uso".formatted(formattedTelefone));
    }
}
