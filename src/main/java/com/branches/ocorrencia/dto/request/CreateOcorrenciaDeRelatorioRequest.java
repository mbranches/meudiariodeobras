package com.branches.ocorrencia.dto.request;

import com.branches.relatorio.dto.request.CampoPersonalizadoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.util.List;

public record CreateOcorrenciaDeRelatorioRequest(
        @NotBlank(message = "O campo 'descricao' é obrigatório")
        String descricao,
        List<Long> tiposOcorrenciaIds,
        LocalTime horaInicio,
        LocalTime horaFim,
        @Valid
        List<CampoPersonalizadoRequest> camposPersonalizados
) {
}
