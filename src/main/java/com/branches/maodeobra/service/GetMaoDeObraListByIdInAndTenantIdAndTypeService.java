package com.branches.maodeobra.service;

import com.branches.exception.NotFoundException;
import com.branches.maodeobra.domain.MaoDeObraEntity;
import com.branches.maodeobra.repository.MaoDeObraRepository;
import com.branches.obra.domain.enums.TipoMaoDeObra;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class GetMaoDeObraListByIdInAndTenantIdAndTypeService {
    private final MaoDeObraRepository maoDeObraRepository;

    public List<MaoDeObraEntity> execute(Set<Long> ids, Long tenantId, TipoMaoDeObra type) {
        List<MaoDeObraEntity> response = maoDeObraRepository.findAllByIdInAndTenantIdAndTipoAndAtivoIsTrue(
                ids,
                tenantId,
                type
        );

        if (response.size() != ids.size()) {
            List<Long> notFoundIds = new ArrayList<>(ids);
            notFoundIds.removeAll(response.stream().map(MaoDeObraEntity::getId).toList());

            throw new NotFoundException("Mão de obra não encontrada com o(s) id(s): " + notFoundIds);
        }

        return response;
    }
}
