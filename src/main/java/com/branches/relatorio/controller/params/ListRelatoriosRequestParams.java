package com.branches.relatorio.controller.params;

import com.branches.relatorio.domain.enums.StatusRelatorio;

import java.time.LocalDate;

public record ListRelatoriosRequestParams(
        String obraId,
        StatusRelatorio status,
        Long numero,
        LocalDate dataInicio
) {
}
