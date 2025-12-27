package com.branches.arquivo.dto.response;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.relatorio.domain.RelatorioEntity;
import com.branches.relatorio.dto.response.RelatorioResumidoResponse;

import java.util.List;

public record ArquivosDeObraPorRelatorioResponse(
        RelatorioResumidoResponse relatorio,
        List<ArquivoResponse> arquivos
) {
    public static ArquivosDeObraPorRelatorioResponse from(RelatorioEntity relatorio, List<ArquivoEntity> arquivo) {
        return new ArquivosDeObraPorRelatorioResponse(
                RelatorioResumidoResponse.from(relatorio),
                arquivo.stream().map(ArquivoResponse::from).toList()
        );
    }
}
