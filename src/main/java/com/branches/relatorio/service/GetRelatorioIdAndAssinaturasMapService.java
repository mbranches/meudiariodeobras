package com.branches.relatorio.service;

import com.branches.relatorio.domain.AssinaturaDeRelatorioEntity;
import com.branches.relatorio.repository.AssinaturaDeRelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndAssinaturasMapService {
    private final AssinaturaDeRelatorioRepository assinaturaDeRelatorioRepository;

    public Map<Long, List<AssinaturaDeRelatorioEntity>> execute(List<RelatorioDetailsProjection> relatorios) {
        List<Long> relatoriosIdsToQuery = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .toList();
        List<AssinaturaDeRelatorioEntity> assinaturas = assinaturaDeRelatorioRepository.findAllByRelatorioIdIn(relatoriosIdsToQuery);

        Map<Long, List<AssinaturaDeRelatorioEntity>> map = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));

        assinaturas.forEach(assinatura ->
                map.get(assinatura.getRelatorio().getId()).add(assinatura)
        );

        return map;
    }
}
