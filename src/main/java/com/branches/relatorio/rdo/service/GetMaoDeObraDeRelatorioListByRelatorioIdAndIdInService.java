package com.branches.relatorio.rdo.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.rdo.domain.MaoDeObraDeRelatorioEntity;
import com.branches.relatorio.rdo.repository.MaoDeObraDeRelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetMaoDeObraDeRelatorioListByRelatorioIdAndIdInService {
    private final MaoDeObraDeRelatorioRepository maoDeObraDeRelatorioRepository;

    public List<MaoDeObraDeRelatorioEntity> execute(Long relatorioId, Collection<Long> maoDeObraDeRelatorioIds) {
        List<MaoDeObraDeRelatorioEntity> response = maoDeObraDeRelatorioRepository.findAllByIdInAndRelatorioId(maoDeObraDeRelatorioIds, relatorioId);

        if (response.size() != maoDeObraDeRelatorioIds.size()) {
            maoDeObraDeRelatorioIds.removeAll(response.stream().map(MaoDeObraDeRelatorioEntity::getId).toList());

            throw new NotFoundException("Mão de obra de relatório não encontrada(s) com o(s) id(s): " + maoDeObraDeRelatorioIds);
        }

        return response;
    }
}
