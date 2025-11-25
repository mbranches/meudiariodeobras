package com.branches.relatorio.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.repository.RelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioWithObraProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetRelatorioWithObraByIdExternoAndTenantIdService {
    private final RelatorioRepository relatorioRepository;

    public RelatorioWithObraProjection execute(String relatorioExternalId, Long tenantId) {
        return relatorioRepository.findRelatorioWithObraByIdExternoAndTenantId(relatorioExternalId, tenantId)
                .orElseThrow(() -> new NotFoundException("Relatório não encontrado com o id: " + relatorioExternalId));
    }
}
