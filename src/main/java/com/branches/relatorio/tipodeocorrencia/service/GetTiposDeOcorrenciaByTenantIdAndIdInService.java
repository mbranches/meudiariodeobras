package com.branches.relatorio.tipodeocorrencia.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.tipodeocorrencia.domain.TipoDeOcorrenciaEntity;
import com.branches.relatorio.tipodeocorrencia.repository.TipoDeOcorrenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetTiposDeOcorrenciaByTenantIdAndIdInService {
    private final TipoDeOcorrenciaRepository tipoDeOcorrenciaRepository;

    public List<TipoDeOcorrenciaEntity> execute(Long tenantId, Collection<Long> ids) {
        List<TipoDeOcorrenciaEntity> response = tipoDeOcorrenciaRepository.findAllByIdInAndTenantId(ids, tenantId);

        if (response.size() != ids.size()) {
            ids.removeAll(response.stream().map(TipoDeOcorrenciaEntity::getId).toList());

            throw new NotFoundException("Tipo(s) de ocorrencia nao encontrado(s) para o(s) id(s): " + ids);
        }
        return response;
    }
}
