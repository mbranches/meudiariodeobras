package com.branches.comentarios.dto.response;

import com.branches.comentarios.model.ComentarioDeRelatorioEntity;
import com.branches.relatorio.dto.response.CampoPersonalizadoResponse;

import java.util.List;

public record CreateComentarioDeRelatorioResponse(
        Long id,
        String descricao,
        List<CampoPersonalizadoResponse> camposPersonalizados
) {
    public static CreateComentarioDeRelatorioResponse from(ComentarioDeRelatorioEntity comentarioDeRelatorioEntity) {
        var camposPersonalizadosResponse = comentarioDeRelatorioEntity.getCamposPersonalizados() != null ?
                comentarioDeRelatorioEntity.getCamposPersonalizados().stream()
                        .map(CampoPersonalizadoResponse::from)
                        .toList() : null;

        return new CreateComentarioDeRelatorioResponse(
                comentarioDeRelatorioEntity.getId(),
                comentarioDeRelatorioEntity.getDescricao(),
                camposPersonalizadosResponse
        );
    }
}
