package com.example.leets7th.domain.report.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReportSuccessCode implements BaseCode {
    Report_POST_SUCCESS(HttpStatus.OK,
            "REPORT200_5",
            "게시글 신고에 성공했습니다."),
    PROCESS_REPORT_SUCCESS(HttpStatus.OK,
            "REPORT200_6",
            "신고 처리에 성공했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
