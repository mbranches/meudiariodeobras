package com.branches.obra.adapter;

import com.branches.obra.domain.ObraEntity;
import com.branches.obra.port.LoadObraPort;
import com.branches.obra.port.WriteObraPort;
import com.branches.obra.repository.ObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ObraPersistenceAdapter implements LoadObraPort, WriteObraPort {
    private final ObraRepository obraRepository;

    @Override
    public Integer getQuantidadeObrasAtivasByTenantId(Long tenantId) {
        return obraRepository.countByTenantIdAndAtivoIsTrue(tenantId);
    }

    @Override
    public ObraEntity save(ObraEntity obraEntity) {
        return obraRepository.save(obraEntity);
    }
}
