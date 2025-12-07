package com.branches.utils;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;

public record PageableRequest(
        @NotNull(message = "O parâmetro 'pageSize' é obrigatório") Integer pageSize,
        @NotNull(message = "O parâmetro 'pageNumber' é obrigatório") Integer pageNumber,
        @NotNull(message = "O parâmetro 'sortDirection' é obrigatório") Sort.Direction sortDirection
) {
}
