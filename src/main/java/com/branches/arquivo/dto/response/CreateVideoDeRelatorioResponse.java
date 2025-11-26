package com.branches.arquivo.dto.response;

import com.branches.arquivo.domain.ArquivoEntity;

public record CreateVideoDeRelatorioResponse(
        Long id,
        String url,
        String descricao
) {
    public static CreateVideoDeRelatorioResponse from(ArquivoEntity arquivo) {
        return new CreateVideoDeRelatorioResponse(
                arquivo.getId(),
                arquivo.getUrl(),
                arquivo.getDescricao()
        );
    }
}

