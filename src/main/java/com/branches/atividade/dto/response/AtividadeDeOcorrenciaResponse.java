package com.branches.atividade.dto.response;

import com.branches.atividade.domain.AtividadeDeRelatorioEntity;

public record AtividadeDeOcorrenciaResponse(
        Long id,
        String descricao
) {
    public static AtividadeDeOcorrenciaResponse from(AtividadeDeRelatorioEntity atividadeVinculada) {
        return new AtividadeDeOcorrenciaResponse(
                atividadeVinculada.getId(),
                atividadeVinculada.getDescricao()
        );
    }
}
