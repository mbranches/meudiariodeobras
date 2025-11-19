package com.branches.relatorio.rdo.dto.response;

import com.branches.relatorio.rdo.domain.CampoPersonalizadoEntity;

public record CampoPersonalizadoResponse(
        String campo,
        String descricao
) {
    public static CampoPersonalizadoResponse from(CampoPersonalizadoEntity campoPersonalizadoEntity) {
        return new CampoPersonalizadoResponse(
                campoPersonalizadoEntity.getCampo(),
                campoPersonalizadoEntity.getDescricao()
        );
    }
}
