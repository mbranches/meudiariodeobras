package com.branches.comentarios.service;

import com.branches.comentarios.model.ComentarioDeRelatorioEntity;
import com.branches.exception.NotFoundException;
import com.branches.comentarios.repository.ComentarioDeRelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetComentarioDeRelatorioByIdAndRelatorioIdService {
    private final ComentarioDeRelatorioRepository comentarioDeRelatorioRepository;
    
    public ComentarioDeRelatorioEntity execute(Long id, Long relatorioId) {
        return comentarioDeRelatorioRepository.findByIdAndRelatorioId(id, relatorioId)
                .orElseThrow(() -> new NotFoundException("Comentário de relatório não encontrado com o id: " + id));
    }
}
