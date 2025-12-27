package com.branches.relatorio.dto.response;

import com.branches.relatorio.domain.RelatorioEntity;
import com.branches.relatorio.domain.enums.StatusRelatorio;

import java.time.LocalDate;

public record RelatorioResumidoResponse(
        String id,
        LocalDate dataInicio,
        LocalDate dataFim,
        Long numero,
        StatusRelatorio status
) {
    public static RelatorioResumidoResponse from(RelatorioEntity relatorio) {
        return new RelatorioResumidoResponse(
                relatorio.getIdExterno(),
                relatorio.getDataInicio(),
                relatorio.getDataFim(),
                relatorio.getNumero(),
                relatorio.getStatus()
        );
    }
}
