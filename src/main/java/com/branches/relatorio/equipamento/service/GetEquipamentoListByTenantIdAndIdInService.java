package com.branches.relatorio.equipamento.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.equipamento.domain.EquipamentoEntity;
import com.branches.relatorio.equipamento.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetEquipamentoListByTenantIdAndIdInService {
    private final EquipamentoRepository equipamentoRepository;

    public List<EquipamentoEntity> execute(Long tenantId, Collection<Long> equipamentosIds) {
        List<EquipamentoEntity> response = equipamentoRepository.findAllByIdInAndTenantIdAndAtivoIsTrue(equipamentosIds, tenantId);

        if (response.size() != equipamentosIds.size()) {
            equipamentosIds.removeAll(response.stream().map(EquipamentoEntity::getId).toList());

            throw new NotFoundException("Equipamento(s) não encontrado(s) com id(s): " + equipamentosIds);
        }

        return response;
    }
}
