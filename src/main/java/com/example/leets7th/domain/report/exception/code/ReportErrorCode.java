package com.example.leets7th.domain.report.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportErrorCode implements BaseCode {
    ALREADY_REPORTED(HttpStatus.BAD_REQUEST,
            "REPORT400_1",
            "이미 신고한 게시글입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
