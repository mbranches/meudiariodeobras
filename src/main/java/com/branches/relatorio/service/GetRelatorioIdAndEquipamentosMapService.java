package com.branches.relatorio.service;

import com.branches.equipamento.domain.EquipamentoDeRelatorioEntity;
import com.branches.equipamento.repository.EquipamentoDeRelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndEquipamentosMapService {
    private final EquipamentoDeRelatorioRepository equipamentoDeRelatorioRepository;

    public Map<Long, List<EquipamentoDeRelatorioEntity>> execute(List<RelatorioDetailsProjection> relatorios) {
        List<Long> relatoriosIdsToQuery = relatorios.stream()
                .filter(RelatorioDetailsProjection::getShowEquipamentos)
                .map(RelatorioDetailsProjection::getId)
                .toList();
        List<EquipamentoDeRelatorioEntity> equipamentos = equipamentoDeRelatorioRepository.findAllByRelatorioIdIn(relatoriosIdsToQuery);

        Map<Long, List<EquipamentoDeRelatorioEntity>> map = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));

        equipamentos.forEach(equipamento ->
                map.get(equipamento.getRelatorio().getId()).add(equipamento)
        );

        return map;
    }

}
