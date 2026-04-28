package com.leets.blog.report.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportTargetType {
    POST("게시물"),
    COMMENT("댓글");

    private final String description;
}
