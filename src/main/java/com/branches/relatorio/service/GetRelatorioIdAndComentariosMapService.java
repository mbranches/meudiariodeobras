package com.branches.relatorio.service;

import com.branches.comentarios.model.ComentarioDeRelatorioEntity;
import com.branches.comentarios.repository.ComentarioDeRelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndComentariosMapService {
    private final ComentarioDeRelatorioRepository comentarioDeRelatorioRepository;

    public Map<Long, List<ComentarioDeRelatorioEntity>> execute(List<RelatorioDetailsProjection> relatorios) {
        List<Long> relatoriosIdsToQuery = relatorios.stream()
                .filter(RelatorioDetailsProjection::getShowComentarios)
                .map(RelatorioDetailsProjection::getId)
                .toList();
        List<ComentarioDeRelatorioEntity> comentarios = comentarioDeRelatorioRepository.findAllByRelatorioIdIn(relatoriosIdsToQuery);

        Map<Long, List<ComentarioDeRelatorioEntity>> map = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));

        comentarios.forEach(comentario ->
                map.get(comentario.getRelatorio().getId()).add(comentario)
        );

        return map;
    }
}
