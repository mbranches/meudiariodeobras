package com.branches.relatorio.rdo.dto.request;

import jakarta.validation.constraints.NotNull;

public record MaoDeObraDeAtividadeRequest(
        Long id,
        @NotNull(message = "O campo 'maoDeObraId' é obrigatório")
        Long maoDeObraId
) {
}
