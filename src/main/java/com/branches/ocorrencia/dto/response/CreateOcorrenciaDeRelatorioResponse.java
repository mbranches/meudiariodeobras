package com.branches.ocorrencia.dto.response;

import com.branches.atividade.dto.response.AtividadeDeOcorrenciaResponse;
import com.branches.ocorrencia.domain.OcorrenciaDeRelatorioCampoPersonalizadoEntity;
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
        Integer minutosTotais,
        AtividadeDeOcorrenciaResponse atividadeVinculada,
        List<CampoPersonalizadoResponse> camposPersonalizados
) {
    public static CreateOcorrenciaDeRelatorioResponse from(OcorrenciaDeRelatorioEntity ocorrenciaDeRelatorioEntity) {
        var tiposOcorrencia = ocorrenciaDeRelatorioEntity.getTiposDeOcorrencia() != null ?
                ocorrenciaDeRelatorioEntity.getTiposDeOcorrencia().stream().map(TipoDeOcorrenciaResponse::from).toList()
                : null;

        var camposPersonalizados = ocorrenciaDeRelatorioEntity.getCamposPersonalizados() != null ?
                ocorrenciaDeRelatorioEntity.getCamposPersonalizados().stream()
                        .map(OcorrenciaDeRelatorioCampoPersonalizadoEntity::getCampoPersonalizado)
                        .map(CampoPersonalizadoResponse::from)
                        .toList() : null;

        AtividadeDeOcorrenciaResponse atividadeVinculadaResponse = ocorrenciaDeRelatorioEntity.getAtividadeVinculada() != null ?
                AtividadeDeOcorrenciaResponse.from(ocorrenciaDeRelatorioEntity.getAtividadeVinculada()) : null;

        return new CreateOcorrenciaDeRelatorioResponse(
                ocorrenciaDeRelatorioEntity.getId(),
                ocorrenciaDeRelatorioEntity.getDescricao(),
                tiposOcorrencia,
                ocorrenciaDeRelatorioEntity.getHoraInicio(),
                ocorrenciaDeRelatorioEntity.getHoraFim(),
                ocorrenciaDeRelatorioEntity.getMinutosTotais(),
                atividadeVinculadaResponse,
                camposPersonalizados
        );
    }
}
