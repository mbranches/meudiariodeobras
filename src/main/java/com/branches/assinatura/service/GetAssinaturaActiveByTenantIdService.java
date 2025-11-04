package com.branches.assinatura.service;

import com.branches.assinatura.port.LoadAssinaturaPort;
import com.branches.shared.dto.AssinaturaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetAssinaturaActiveByTenantIdService {
    private final LoadAssinaturaPort loadAssinaturaPort;

    public AssinaturaDto execute(Long tenantId) {
        return AssinaturaDto.from(loadAssinaturaPort.getAssinaturaAtivaByTenantId(tenantId));
    }
}
