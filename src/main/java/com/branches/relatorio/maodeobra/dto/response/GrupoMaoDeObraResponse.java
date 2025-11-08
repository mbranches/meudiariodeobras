package com.branches.relatorio.maodeobra.dto.response;

import com.branches.relatorio.maodeobra.domain.GrupoMaoDeObraEntity;

public record GrupoMaoDeObraResponse(Long id, String descricao) {
    public static GrupoMaoDeObraResponse from(GrupoMaoDeObraEntity grupo) {
        return new GrupoMaoDeObraResponse(grupo.getId(), grupo.getDescricao());
    }
}


