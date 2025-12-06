package com.branches.utils;

import org.springframework.data.domain.Sort;

public record PageableRequest(
        int pageSize,
        int pageNumber,
        Sort.Direction sort
) {
}
