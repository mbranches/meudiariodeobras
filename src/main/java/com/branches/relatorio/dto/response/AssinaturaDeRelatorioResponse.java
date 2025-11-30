package com.branches.relatorio.dto.response;

import com.branches.relatorio.domain.AssinaturaDeRelatorioEntity;

public record AssinaturaDeRelatorioResponse(
        Long id,
        String nomeAssinante,
        String assinaturaUrl
) {
    public static AssinaturaDeRelatorioResponse from(AssinaturaDeRelatorioEntity assinaturaDeRelatorioEntity) {
        return new AssinaturaDeRelatorioResponse(
                assinaturaDeRelatorioEntity.getId(),
                assinaturaDeRelatorioEntity.getConfiguracao().getNomeAssinante(),
                assinaturaDeRelatorioEntity.getAssinaturaUrl()
        );
    }
}
