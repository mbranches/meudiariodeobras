package com.branches.relatorio.service;

import com.branches.material.domain.MaterialDeRelatorioEntity;
import com.branches.material.repository.MaterialDeRelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndMateriaisMapService {
    private final MaterialDeRelatorioRepository materialDeRelatorioRepository;

    public Map<Long, List<MaterialDeRelatorioEntity>> execute(List<RelatorioDetailsProjection> relatorios) {
        List<Long> relatoriosIdsToQuery = relatorios.stream()
                .filter(RelatorioDetailsProjection::getShowMateriais)
                .map(RelatorioDetailsProjection::getId)
                .toList();
        List<MaterialDeRelatorioEntity> materiais = materialDeRelatorioRepository.findAllByRelatorioIdIn(relatoriosIdsToQuery);

        Map<Long, List<MaterialDeRelatorioEntity>> map = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));

        materiais.forEach(material ->
                map.get(material.getRelatorio().getId()).add(material)
        );

        return map;
    }
}
