package com.branches.relatorio.rdo.dto.response;

import com.branches.relatorio.rdo.domain.AtividadeDeRelatorioEntity;

public record AtividadeByOcorrenciaDeRelatorioResponse(
        Long id,
        String descricao
) {
    public static AtividadeByOcorrenciaDeRelatorioResponse from(AtividadeDeRelatorioEntity atividadeVinculada) {
        return new AtividadeByOcorrenciaDeRelatorioResponse(
                atividadeVinculada.getId(),
                atividadeVinculada.getDescricao()
        );
    }
}
