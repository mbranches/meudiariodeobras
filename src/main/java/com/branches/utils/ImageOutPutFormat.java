package com.branches.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageOutPutFormat {
    JPEG("jpeg"),
    PNG("png");

    private final String format;
}
