package com.branches.relatorio.dto.response;

import com.branches.relatorio.domain.RelatorioEntity;

import java.util.List;

public record ItemPorRelatorioResponse<T>(
        RelatorioResumidoResponse relatorio,
        List<T> arquivos
) {
    public static <T> ItemPorRelatorioResponse<T> from(RelatorioEntity relatorio, List<T> items) {
        return new ItemPorRelatorioResponse<>(
                RelatorioResumidoResponse.from(relatorio),
                items
        );
    }
}
