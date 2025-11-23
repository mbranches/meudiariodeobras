package com.branches.material.service;

import com.branches.exception.NotFoundException;
import com.branches.material.domain.MaterialDeRelatorioEntity;
import com.branches.relatorio.repository.MaterialDeRelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetMaterialByIdAndRelatorioIdService {
    private final MaterialDeRelatorioRepository materialDeRelatorioRepository;

    public MaterialDeRelatorioEntity execute(Long id, Long relatorioId) {
        return materialDeRelatorioRepository.findByIdAndRelatorioId(id, relatorioId)
                .orElseThrow(() -> new NotFoundException("Material de relatório não encontrado com o id: " + id));
    }
}
