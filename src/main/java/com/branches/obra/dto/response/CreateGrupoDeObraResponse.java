package com.branches.obra.dto.response;

import com.branches.obra.domain.GrupoDeObraEntity;

public record CreateGrupoDeObraResponse(
        Long id,
        String descricao
) {
    public static CreateGrupoDeObraResponse from(GrupoDeObraEntity saved) {
        return new CreateGrupoDeObraResponse(
                saved.getId(),
                saved.getDescricao()
        );
    }
}
