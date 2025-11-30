package com.branches.obra.dto.response;

import com.branches.obra.domain.ConfiguracaoDeAssinaturaDeRelatorioEntity;

public record ConfiguracaoDeAssinaturaDeRelatorioResponse(
        Long id,
        String nomeAssinante
) {
    public static ConfiguracaoDeAssinaturaDeRelatorioResponse from(ConfiguracaoDeAssinaturaDeRelatorioEntity entity) {
        return new ConfiguracaoDeAssinaturaDeRelatorioResponse(
                entity.getId(),
                entity.getNomeAssinante()
        );
    }
}
