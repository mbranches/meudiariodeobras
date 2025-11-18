package com.branches.relatorio.rdo.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.rdo.domain.EquipamentoDeRelatorioEntity;
import com.branches.relatorio.rdo.repository.EquipamentoDeRelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetEquipamentoDeRelatorioListByRelatorioIdAndIdInService {
    private final EquipamentoDeRelatorioRepository equipamentoDeRelatorioRepository;

    public List<EquipamentoDeRelatorioEntity> execute(Long relatorioId, Collection<Long> ids) {
        List<EquipamentoDeRelatorioEntity> response = equipamentoDeRelatorioRepository.findAllByIdInAndRelatorioId(ids, relatorioId);

        if (response.size() != ids.size()) {
            ids.removeAll(response.stream().map(EquipamentoDeRelatorioEntity::getId).toList());

            throw new NotFoundException("Equipamento(s) de relatorio nao encontrado(s) para o(s) id(s): " + ids);
        }

        return response;
    }
}
