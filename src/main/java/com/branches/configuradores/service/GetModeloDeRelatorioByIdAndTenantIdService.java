package com.branches.configuradores.service;

import com.branches.configuradores.domain.ModeloDeRelatorioEntity;
import com.branches.configuradores.repositorio.ModeloDeRelatorioRepository;
import com.branches.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetModeloDeRelatorioByIdAndTenantIdService {
    private final ModeloDeRelatorioRepository modeloDeRelatorioRepository;

    public ModeloDeRelatorioEntity execute(Long id, Long tenantId) {
        return modeloDeRelatorioRepository.findByIdAndTenantIdAndAtivoIsTrue(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Modelo de Relatório não encontrado com o id: %s".formatted(id)));
    }
}
