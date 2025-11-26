package com.branches.relatorio.dto.response;

import com.branches.obra.domain.LogoDeRelatorioEntity;

public record LogoDeRelatorioResponse(
        Long id,
        String url,
        Boolean isLogoDoTenant,
        Boolean exibir
) {
    public static LogoDeRelatorioResponse from(LogoDeRelatorioEntity entity) {
        return new LogoDeRelatorioResponse(
                entity.getId(),
                entity.getUrl(),
                entity.getIsLogoDoTenant(),
                entity.getExibir()
        );
    }
}
