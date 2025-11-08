package com.branches.obra.controller;

import jakarta.validation.constraints.NotBlank;

public record CreateGrupoDeObraRequest(
        @NotBlank(message = "O campo 'descricao' é obrigatório") String descricao) {
}