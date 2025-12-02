package com.branches.condicaoclimatica.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CondicaoDoTempo {
    PRATICAVEL("Praticável"),
    NAO_PRATICAVEL("Não Praticável");

    private final String descricao;
}
