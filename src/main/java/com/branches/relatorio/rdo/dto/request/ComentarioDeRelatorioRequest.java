package com.branches.relatorio.rdo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ComentarioDeRelatorioRequest(
    Long id,
    @NotBlank(message = "O campo 'descricao' é obrigatório")
    String descricao,
    @Valid
    List<CampoPersonalizadoRequest> camposPersonalizados
    ) {}
