package com.branches.relatorio.ocorrencia.dto.response;

import com.branches.relatorio.ocorrencia.domain.TipoDeOcorrenciaEntity;

public record TipoDeOcorrenciaResponse(Long id, String descricao) {
    public static TipoDeOcorrenciaResponse from(TipoDeOcorrenciaEntity tipoDeOcorrencia) {
        return new TipoDeOcorrenciaResponse(tipoDeOcorrencia.getId(), tipoDeOcorrencia.getDescricao());
    }
}
