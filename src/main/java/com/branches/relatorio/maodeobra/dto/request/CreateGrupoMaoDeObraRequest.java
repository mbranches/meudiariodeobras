package com.branches.relatorio.maodeobra.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateGrupoMaoDeObraRequest(
        @NotBlank(message = "O campo 'descricao' é obrigatório") String descricao) {
}