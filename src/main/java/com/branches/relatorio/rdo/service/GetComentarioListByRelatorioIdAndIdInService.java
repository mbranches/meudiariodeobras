package com.branches.relatorio.rdo.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.rdo.domain.ComentarioDeRelatorioEntity;
import com.branches.relatorio.rdo.repository.ComentarioDeRelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetComentarioListByRelatorioIdAndIdInService {
    private final ComentarioDeRelatorioRepository comentarioDeRelatorioRepository;

    public List<ComentarioDeRelatorioEntity> execute(Long relatorioId, Collection<Long> ids) {
        List<ComentarioDeRelatorioEntity> response = comentarioDeRelatorioRepository.findAllByIdInAndRelatorioId(ids, relatorioId);

        if (response.size() != ids.size()) {
            ids.removeAll(response.stream().map(ComentarioDeRelatorioEntity::getId).toList());

            throw new NotFoundException("Comentario(s) de relatorio nao encontrado(s) para o(s) id(s): " + ids);
        }

        return response;
    }
}
