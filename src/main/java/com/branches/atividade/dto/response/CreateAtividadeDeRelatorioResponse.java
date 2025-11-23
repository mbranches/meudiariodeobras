package com.branches.atividade.dto.response;

import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import com.branches.atividade.domain.enums.StatusAtividade;
import com.branches.maodeobra.dto.response.MaoDeObraDeAtividadeResponse;
import com.branches.relatorio.dto.response.CampoPersonalizadoResponse;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public record CreateAtividadeDeRelatorioResponse(
        Long id,
        String descricao,
        Integer quantidadeRealizada,
        String unidadeMedida,
        BigDecimal porcentagemConcluida,
        StatusAtividade status,
        LocalTime horaInicio,
        LocalTime horaFim,
        LocalTime totalHoras,
        List<MaoDeObraDeAtividadeResponse> maoDeObra,
        List<CampoPersonalizadoResponse> camposPersonalizados

) {
    public static CreateAtividadeDeRelatorioResponse from(AtividadeDeRelatorioEntity saved) {
        List<MaoDeObraDeAtividadeResponse> maoDeObraResponse = saved.getMaoDeObra() != null ? saved.getMaoDeObra().stream()
                .map(MaoDeObraDeAtividadeResponse::from)
                .toList() : null;

        List<CampoPersonalizadoResponse> camposPersonalizadosResponse = saved.getCamposPersonalizados() != null ? saved.getCamposPersonalizados().stream()
                .map(CampoPersonalizadoResponse::from)
                .toList() : null;

        return new CreateAtividadeDeRelatorioResponse(
                saved.getId(),
                saved.getDescricao(),
                saved.getQuantidadeRealizada(),
                saved.getUnidadeMedida(),
                saved.getPorcentagemConcluida(),
                saved.getStatus(),
                saved.getHoraInicio(),
                saved.getHoraFim(),
                saved.getTotalHoras(),
                maoDeObraResponse,
                camposPersonalizadosResponse
        );
    }
}
