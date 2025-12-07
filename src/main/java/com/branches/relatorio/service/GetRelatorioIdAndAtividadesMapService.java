package com.branches.relatorio.service;

import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import com.branches.atividade.repository.AtividadeDeRelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndAtividadesMapService {
    private final AtividadeDeRelatorioRepository atividadeDeRelatorioRepository;

    public Map<Long, List<AtividadeDeRelatorioEntity>> execute(List<RelatorioDetailsProjection> relatorios) {
        List<Long> relatoriosIdsToQuery = relatorios.stream()
                .filter(RelatorioDetailsProjection::getShowAtividades)
                .map(RelatorioDetailsProjection::getId)
                .toList();
        List<AtividadeDeRelatorioEntity> atividades = atividadeDeRelatorioRepository.findAllByRelatorioIdIn(relatoriosIdsToQuery);

        Map<Long, List<AtividadeDeRelatorioEntity>> map = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));

        atividades.forEach(atividade ->
                map.get(atividade.getRelatorio().getId()).add(atividade)
        );

        return map;
    }
}
