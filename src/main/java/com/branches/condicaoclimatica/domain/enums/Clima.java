package com.branches.condicaoclimatica.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Clima {
    CLARO("Claro"),
    NUBLADO("Nublado"),
    CHUVOSO("Chuvoso");

    private final String descricao;
}
