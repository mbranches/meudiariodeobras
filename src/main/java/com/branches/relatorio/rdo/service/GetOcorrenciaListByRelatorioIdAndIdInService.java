package com.branches.relatorio.rdo.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.rdo.domain.OcorrenciaDeRelatorioEntity;
import com.branches.relatorio.rdo.repository.OcorrenciaDeRelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetOcorrenciaListByRelatorioIdAndIdInService {
    private final OcorrenciaDeRelatorioRepository ocorrenciaDeRelatorioRepository;

    public List<OcorrenciaDeRelatorioEntity> execute(Long relatorioId, Collection<Long> ids) {
        var response = ocorrenciaDeRelatorioRepository.findAllByIdInAndRelatorioId(ids, relatorioId);

        if (response.size() != ids.size()) {
            ids.removeAll(response.stream().map(OcorrenciaDeRelatorioEntity::getId).toList());

            throw new NotFoundException("Ocorrencia(s) de relatorio nao encontrado(s) para o(s) id(s): " + ids);
        }

        return response;
    }
}
