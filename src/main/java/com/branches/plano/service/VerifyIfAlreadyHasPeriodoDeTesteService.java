package com.branches.plano.service;

import com.branches.plano.repository.PeriodoTesteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VerifyIfAlreadyHasPeriodoDeTesteService {
    private final PeriodoTesteRepository periodoTesteRepository;

    public boolean execute(List<Long> tenantIds) {
        return periodoTesteRepository.existsByTenantIdIn(tenantIds);
    }
}
