package com.branches.relatorio.rdo.dto.response;

import com.branches.relatorio.rdo.domain.RelatorioEntity;

public record CreateRelatorioResponse(
        String id
) {
    public static CreateRelatorioResponse from(RelatorioEntity relatorio) {
        return new CreateRelatorioResponse(relatorio.getIdExterno());
    }
}
