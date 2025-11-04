package com.branches.assinatura.adapter;

import com.branches.assinatura.domain.AssinaturaEntity;
import com.branches.assinatura.domain.enums.AssinaturaStatus;
import com.branches.assinatura.port.LoadAssinaturaPort;
import com.branches.assinatura.repository.AssinaturaRepository;
import com.branches.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AssinaturaPersistenceAdapter implements LoadAssinaturaPort {
    private final AssinaturaRepository assinaturaRepository;

    @Override
    public AssinaturaEntity getAssinaturaAtivaByTenantId(Long tenantId) {
        return assinaturaRepository.findByStatusAndTenantId(AssinaturaStatus.ATIVO, tenantId)
                .orElseThrow(() -> new NotFoundException("Não foi encontrada assinatura ativa para o tenantId: " + tenantId));
    }
}
