package com.branches.relatorio.service;

import com.branches.ocorrencia.domain.OcorrenciaDeRelatorioEntity;
import com.branches.ocorrencia.repository.OcorrenciaDeRelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndOcorrenciasMapService {
    private final OcorrenciaDeRelatorioRepository ocorrenciaDeRelatorioRepository;

    public Map<Long, List<OcorrenciaDeRelatorioEntity>> execute(List<RelatorioDetailsProjection> relatorios) {
        List<Long> relatoriosIdsToQuery = relatorios.stream()
                .filter(RelatorioDetailsProjection::getShowOcorrencias)
                .map(RelatorioDetailsProjection::getId)
                .toList();
        List<OcorrenciaDeRelatorioEntity> ocorrencias = ocorrenciaDeRelatorioRepository.findAllByRelatorioIdIn(relatoriosIdsToQuery);

        Map<Long, List<OcorrenciaDeRelatorioEntity>> map = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));

        ocorrencias.forEach(ocorrencia ->
                map.get(ocorrencia.getRelatorio().getId()).add(ocorrencia)
        );

        return map;
    }
}
