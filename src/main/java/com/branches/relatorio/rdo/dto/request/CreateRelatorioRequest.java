package com.branches.relatorio.rdo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateRelatorioRequest(
        @NotBlank(message = "O campo 'obraId' é obrigatório")
        String obraId,
        @NotNull(message = "O campo 'data' é obrigatório")
        LocalDate data,
        @NotNull(message = "O campo 'copiarInformacoesDoUltimoRelatorio' é obrigatório")
        Boolean copiarInformacoesDoUltimoRelatorio,
        Boolean copiarCondicoesClimaticas,
        Boolean copiarMaoDeObra,
        Boolean copiarEquipamentos,
        Boolean copiarAtividades,
        Boolean copiarOcorrencias,
        Boolean copiarComentarios
) {
}
