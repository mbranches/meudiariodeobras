package com.branches.comentarios.dto.response;

import com.branches.comentarios.model.ComentarioDeRelatorioEntity;

import java.time.LocalDateTime;

public record ComentarioDeRelatorioResponse(
        Long id,
        String descricao,
        AutorResponse autor,
        LocalDateTime dataCriacao
) {
    public static ComentarioDeRelatorioResponse from(ComentarioDeRelatorioEntity comentarioDeRelatorioEntity) {
        return new ComentarioDeRelatorioResponse(
                comentarioDeRelatorioEntity.getId(),
                comentarioDeRelatorioEntity.getDescricao(),
                AutorResponse.from(comentarioDeRelatorioEntity.getAutor()),
                comentarioDeRelatorioEntity.getDataCriacao()
        );
    }
}
