package com.branches.relatorio.ocorrencia.dto.response;

import com.branches.relatorio.ocorrencia.domain.TipoDeOcorrenciaEntity;

public record CreateTipoDeOcorrenciaResponse(
        Long id,
        String descricao
) {
    public static CreateTipoDeOcorrenciaResponse from(TipoDeOcorrenciaEntity saved) {
        return new CreateTipoDeOcorrenciaResponse(
                saved.getId(),
                saved.getDescricao()
        );
    }
}
