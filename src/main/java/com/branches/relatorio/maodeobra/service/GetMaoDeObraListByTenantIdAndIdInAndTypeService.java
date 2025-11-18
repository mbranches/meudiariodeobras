package com.branches.relatorio.maodeobra.service;

import com.branches.exception.NotFoundException;
import com.branches.obra.domain.enums.TipoMaoDeObra;
import com.branches.relatorio.maodeobra.domain.MaoDeObraEntity;
import com.branches.relatorio.maodeobra.repository.MaoDeObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetMaoDeObraListByTenantIdAndIdInAndTypeService {
    private final MaoDeObraRepository maoDeObraRepository;

    public List<MaoDeObraEntity> execute(Long tenantId, List<Long> ids, TipoMaoDeObra type) {
        List<MaoDeObraEntity> response = maoDeObraRepository.findAllByIdInAndTenantIdAndTipoAndAtivoIsTrue(ids, tenantId, type);

        if (response.size() != ids.size()) {
            ids.removeAll(response.stream().map(MaoDeObraEntity::getId).toList());

            throw new NotFoundException("Mão de obra não encontrada(s) com o(s) id(s): " + ids);
        }

        return response;
    }
}
