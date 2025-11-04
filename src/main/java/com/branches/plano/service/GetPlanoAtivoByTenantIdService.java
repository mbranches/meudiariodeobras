package com.branches.plano.service;

import com.branches.plano.port.LoadPlanoPort;
import com.branches.assinatura.service.GetAssinaturaActiveByTenantIdService;
import com.branches.plano.domain.PlanoEntity;
import com.branches.shared.dto.AssinaturaDto;
import com.branches.shared.dto.PlanoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetPlanoAtivoByTenantIdService {
    private final LoadPlanoPort loadPlano;
    private final GetAssinaturaActiveByTenantIdService getAssinaturaActiveByTenantIdService;

    public PlanoDto execute(Long tenantId) {
        AssinaturaDto assinatura = getAssinaturaActiveByTenantIdService.execute(tenantId);

        PlanoEntity plano = loadPlano.getPlanoById(assinatura.planoId());

        return PlanoDto.from(plano);
    }
}
