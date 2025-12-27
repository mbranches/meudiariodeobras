package com.branches.shared.pagination;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public record PageableRequest(
        @NotNull(message = "O parâmetro 'pageSize' é obrigatório") Integer pageSize,
        @NotNull(message = "O parâmetro 'pageNumber' é obrigatório") Integer pageNumber,
        @NotNull(message = "O parâmetro 'sortDirection' é obrigatório") Sort.Direction sortDirection
) {
    public PageRequest toPageRequest(String... properties) {
        return PageRequest.of(this.pageNumber(), this.pageSize(), this.sortDirection(), properties);
    }
}
