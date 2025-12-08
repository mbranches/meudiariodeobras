package com.branches.relatorio.dto.response;

public record ImprimirRelatorioResponse(
        String url
) {
    public static ImprimirRelatorioResponse from(String url) {
        return new ImprimirRelatorioResponse(url);
    }
}
