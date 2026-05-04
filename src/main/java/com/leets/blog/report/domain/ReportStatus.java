package com.leets.blog.report.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportStatus {
    PENDING("처리 대기"),
    RESOLVED("처리 완료");

    private final String description;
}
