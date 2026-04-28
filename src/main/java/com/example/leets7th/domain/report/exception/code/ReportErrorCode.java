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
            "이미 신고한 게시글입니다."),
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND,
            "REPORT404_1",
            "해당 게시글에 대한 신고가 존재하지 않습니다."),
    SELF_REPORT_NOT_ALLOWED(HttpStatus.BAD_REQUEST,
            "REPORT400_2",
            "자신의 게시글은 신고할 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
