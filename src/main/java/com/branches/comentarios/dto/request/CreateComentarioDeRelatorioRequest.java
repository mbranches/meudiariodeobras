package com.branches.comentarios.dto.request;

import com.branches.relatorio.dto.request.CampoPersonalizadoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateComentarioDeRelatorioRequest(
    @NotBlank(message = "O campo 'descricao' é obrigatório")
    String descricao,
    @Valid
    List<CampoPersonalizadoRequest> camposPersonalizados
) {}
