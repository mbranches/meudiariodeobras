package com.branches.obra.dto.response;

import com.branches.obra.domain.enums.StatusObra;
import com.branches.obra.repository.projections.ObraProjection;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ObraByListAllResponse(
        String id,
        String nome,
        StatusObra status,
        String capaUrl,
        LocalDate dataInicio,
        Long quantityOfRelatorios,
        Long quantityOfFotos,
        BigDecimal prazoPercentualDecorrido,
        LocalDate dataUltimoRelatorio,
        ConfiguracaoRelatoriosResponse configuracaoRelatorios
) {
    public static ObraByListAllResponse from(ObraProjection obra, BigDecimal prazoPercentualDecorrido) {
        return new ObraByListAllResponse(
                obra.getIdExterno(),
                obra.getNome(),
                obra.getStatus(),
                obra.getCapaUrl(),
                obra.getDataInicio(),
                obra.getQuantityOfRelatorios(),
                obra.getQuantityOfFotos(),
                prazoPercentualDecorrido,
                obra.getDataUltimoRelatorio(),
                ConfiguracaoRelatoriosResponse.from(obra.getConfiguracaoRelatorios())
        );
    }
}
