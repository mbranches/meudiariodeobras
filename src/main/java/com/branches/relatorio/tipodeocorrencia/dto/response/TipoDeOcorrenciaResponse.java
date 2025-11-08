package com.branches.relatorio.tipodeocorrencia.dto.response;

import com.branches.relatorio.tipodeocorrencia.domain.TipoDeOcorrenciaEntity;

public record TipoDeOcorrenciaResponse(Long id, String descricao) {
    public static TipoDeOcorrenciaResponse from(TipoDeOcorrenciaEntity tipoDeOcorrencia) {
        return new TipoDeOcorrenciaResponse(tipoDeOcorrencia.getId(), tipoDeOcorrencia.getDescricao());
    }
}
