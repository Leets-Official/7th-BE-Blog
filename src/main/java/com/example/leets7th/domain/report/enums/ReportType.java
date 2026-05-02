package com.example.leets7th.domain.report.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
    ABUSE("욕설/비방"),
    SPAM("스팸/광고"),
    NONCONFORMITY("부적절한 내용");

    private final String description;
}
