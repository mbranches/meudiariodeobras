package com.branches.relatorio.rdo.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.util.List;

public record OcorrenciaDeRelatorioRequest(
        Long id,
        @NotBlank(message = "O campo 'descricao' é obrigatório")
        String descricao,
        List<Long> tiposOcorrenciaIds,
        LocalTime horaInicio,
        LocalTime horaFim,
        @Valid
        List<CampoPersonalizadoRequest> camposPersonalizados
) {
}
