package com.branches.relatorio.rdo.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.rdo.domain.AtividadeDeRelatorioEntity;
import com.branches.relatorio.rdo.repository.AtividadeDeRelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetAtividadeListByRelatorioIdAndIdInService {
    private final AtividadeDeRelatorioRepository atividadeDeRelatorioRepository;

    public List<AtividadeDeRelatorioEntity> execute(Long relatorioId, Collection<Long> ids) {
        List<AtividadeDeRelatorioEntity> response =  atividadeDeRelatorioRepository.findAllByIdInAndRelatorioId(ids, relatorioId);

        if (response.size() != ids.size()) {
            ids.removeAll(response.stream().map(AtividadeDeRelatorioEntity::getId).toList());

            throw new NotFoundException("Atividade(s) de relatorio nao encontrado(s) para o(s) id(s): " + ids);
        }

        return response;
    }
}
