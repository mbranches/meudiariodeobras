package com.branches.relatorio.rdo.dto.request;

import com.branches.relatorio.rdo.domain.enums.Clima;
import com.branches.relatorio.rdo.domain.enums.CondicaoDoTempo;
import jakarta.validation.constraints.NotNull;

public record CaracteristicaDePeriodoDoDiaRequest(
        @NotNull(message = "O campo 'id' é obrigatório")
        Long id,
        @NotNull(message = "O campo 'clima' é obrigatório")
        Clima clima,
        @NotNull(message = "O campo 'condicaoDoTempo' é obrigatório")
        CondicaoDoTempo condicaoDoTempo,
        @NotNull(message = "O campo 'ativo' é obrigatório")
        Boolean ativo
) {}
