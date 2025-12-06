package com.branches.utils;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T> (
    int pageSize,
    int pageNumber,
    long totalElements,
    List<T> content,
    boolean isFirst,
    boolean isLast
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getSize(),
                page.getNumber(),
                page.getTotalElements(),
                page.getContent(),
                page.isFirst(),
                page.isLast()
        );
    }
}
