package com.branches.relatorio.tipodeocorrencia.dto.response;

import com.branches.relatorio.tipodeocorrencia.domain.TipoDeOcorrenciaEntity;

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
