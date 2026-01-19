package com.branches.plano.service;

import com.branches.assinaturadeplano.domain.enums.AssinaturaStatus;
import com.branches.assinaturadeplano.repository.AssinaturaDePlanoRepository;
import com.branches.exception.BadRequestException;
import com.branches.plano.repository.PeriodoTesteRepository;
import com.branches.tenant.domain.TenantEntity;
import com.branches.tenant.service.GetTenantByIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfTenantAlreadyCanIniciarPeriodoTesteService {
    private final PeriodoTesteRepository periodoTesteRepository;
    private final AssinaturaDePlanoRepository assinaturaDePlanoRepository;
    private final GetTenantByIdService getTenantByIdService;

    public void execute(Long tenantId) {
        TenantEntity tenant = getTenantByIdService.execute(tenantId);
        boolean alreadyStartedTrialPeriod = periodoTesteRepository.existsByTenantId(tenantId);

        if (alreadyStartedTrialPeriod) {
            throw new BadRequestException("O tenant já iniciou um período de teste anteriormente");
        }

        boolean alreadyHasActiveSubscription = assinaturaDePlanoRepository.existsByStatusInAndTenantId(AssinaturaStatus.getStatusListThatAlreadyHaveActivePlan(), tenantId);

        if (alreadyHasActiveSubscription) {
            throw new BadRequestException("O tenant já possuiu uma assinatura ativa");
        }

        if(tenant.getFoiCriadoPorUsuarioQueJaTestouOSistema()) {
            throw new BadRequestException("O tenant foi criado por um usuário que já testou o sistema anteriormente");
        }
    }
}
