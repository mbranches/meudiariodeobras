package com.branches.comentarios.dto.response;

import com.branches.comentarios.model.ComentarioDeRelatorioEntity;

public record CreateComentarioDeRelatorioResponse(
        Long id,
        String descricao
) {
    public static CreateComentarioDeRelatorioResponse from(ComentarioDeRelatorioEntity comentarioDeRelatorioEntity) {
        return new CreateComentarioDeRelatorioResponse(
                comentarioDeRelatorioEntity.getId(),
                comentarioDeRelatorioEntity.getDescricao()
        );
    }
}
