package com.branches.maodeobra.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PresencaMaoDeObra {
    PRESENTE("Presente"),
    FALTA("Falta"),
    ATESTADO("Atestado"),
    DESLOCANDO("Deslocando"),
    FALTA_JUSTIFICADA("Falta Justificada"),
    FERIAS("Férias"),
    FOLGA("Folga"),
    LICENCA("Licença"),
    TREINAMENTO("Treinamento"),
    VIAGEM("Viagem");

    private final String descricao;
}
