package com.branches.relatorio.rdo.dto.response;

import java.time.LocalDateTime;

public record ModifyerByRelatorioResponse(
        String nome,
        LocalDateTime dataHora
) {
}
