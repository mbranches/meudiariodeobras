package com.branches.ocorrencia.dto.response;

import com.branches.ocorrencia.domain.OcorrenciaDeRelatorioEntity;
import com.branches.relatorio.dto.response.CampoPersonalizadoResponse;

import java.time.LocalTime;
import java.util.List;

public record CreateOcorrenciaDeRelatorioResponse(
        Long id,
        String descricao,
        List<TipoDeOcorrenciaResponse> tiposOcorrencia,
        LocalTime horaInicio,
        LocalTime horaFim,
        List<CampoPersonalizadoResponse> camposPersonalizados
) {
    public static CreateOcorrenciaDeRelatorioResponse from(OcorrenciaDeRelatorioEntity ocorrenciaDeRelatorioEntity) {
        var tiposOcorrencia = ocorrenciaDeRelatorioEntity.getTiposDeOcorrencia() != null ?
                ocorrenciaDeRelatorioEntity.getTiposDeOcorrencia().stream().map(TipoDeOcorrenciaResponse::from).toList()
                : null;

        var camposPersonalizados = ocorrenciaDeRelatorioEntity.getCamposPersonalizados() != null ?
                ocorrenciaDeRelatorioEntity.getCamposPersonalizados().stream()
                        .map(CampoPersonalizadoResponse::from)
                        .toList() : null;

        return new CreateOcorrenciaDeRelatorioResponse(
                ocorrenciaDeRelatorioEntity.getId(),
                ocorrenciaDeRelatorioEntity.getDescricao(),
                tiposOcorrencia,
                ocorrenciaDeRelatorioEntity.getHoraInicio(),
                ocorrenciaDeRelatorioEntity.getHoraFim(),
                camposPersonalizados
        );
    }
}
