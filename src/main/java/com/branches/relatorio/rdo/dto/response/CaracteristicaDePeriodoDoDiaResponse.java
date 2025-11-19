package com.branches.relatorio.rdo.dto.response;

import com.branches.relatorio.rdo.domain.CaracteristicaDePeriodoDoDiaEntity;
import com.branches.relatorio.rdo.domain.enums.Clima;
import com.branches.relatorio.rdo.domain.enums.CondicaoDoTempo;

public record CaracteristicaDePeriodoDoDiaResponse(
        Long id,
        Clima clima,
        CondicaoDoTempo condicaoDoTempo,
        Boolean ativo
) {
    public static CaracteristicaDePeriodoDoDiaResponse from(CaracteristicaDePeriodoDoDiaEntity caracteristicaManha) {
        return new CaracteristicaDePeriodoDoDiaResponse(
                caracteristicaManha.getId(),
                caracteristicaManha.getClima(),
                caracteristicaManha.getCondicaoDoTempo(),
                caracteristicaManha.getAtivo()
        );
    }
}
