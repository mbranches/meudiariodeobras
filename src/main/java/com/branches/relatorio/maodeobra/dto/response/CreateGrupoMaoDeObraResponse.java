package com.branches.relatorio.maodeobra.dto.response;

import com.branches.relatorio.maodeobra.domain.GrupoMaoDeObraEntity;

public record CreateGrupoMaoDeObraResponse(
        Long id,
        String descricao
) {
    public static CreateGrupoMaoDeObraResponse from(GrupoMaoDeObraEntity saved) {
        return new CreateGrupoMaoDeObraResponse(
                saved.getId(),
                saved.getDescricao()
        );
    }
}

