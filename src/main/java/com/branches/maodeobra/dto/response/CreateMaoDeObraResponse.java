package com.branches.maodeobra.dto.response;

import com.branches.maodeobra.domain.MaoDeObraEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateMaoDeObraResponse(
    Long id,
    String funcao,
    GrupoMaoDeObraResponse grupo,
    String nome,
    LocalTime horaInicio,
    LocalTime horaFim,
    Integer minutosIntervalo,
    Integer minutosTrabalhados
) {
    public static CreateMaoDeObraResponse from(MaoDeObraEntity saved) {
        return new CreateMaoDeObraResponse(
            saved.getId(),
            saved.getFuncao(),
            GrupoMaoDeObraResponse.from(saved.getGrupo()),
            saved.getNome(),
            saved.getHoraInicio(),
            saved.getHoraFim(),
            saved.getMinutosIntervalo(),
            saved.getMinutosTrabalhados()
        );
    }
}