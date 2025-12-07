package com.branches.relatorio.service;

import com.branches.maodeobra.domain.MaoDeObraDeRelatorioEntity;
import com.branches.maodeobra.repository.MaoDeObraDeRelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndMaoDeObraMapService {
    private final MaoDeObraDeRelatorioRepository maoDeObraDeRelatorioRepository;

    public Map<Long, List<MaoDeObraDeRelatorioEntity>> execute(List<RelatorioDetailsProjection> relatorios) {
        List<Long> relatoriosIdsToQuery = relatorios.stream()
                .filter(RelatorioDetailsProjection::getShowMaoDeObra)
                .map(RelatorioDetailsProjection::getId)
                .toList();
        List<MaoDeObraDeRelatorioEntity> maoDeObra = maoDeObraDeRelatorioRepository.findAllByRelatorioIdIn(relatoriosIdsToQuery);

        Map<Long, List<MaoDeObraDeRelatorioEntity>> map = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));

        maoDeObra.forEach(mao ->
                map.get(mao.getRelatorio().getId()).add(mao)
        );

        return map;
    }
}
