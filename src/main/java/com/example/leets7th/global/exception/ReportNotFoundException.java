package com.example.leets7th.global.exception;

public class ReportNotFoundException extends BusinessException {

    public ReportNotFoundException(Long reportId) {
        super(ErrorCode.REPORT_NOT_FOUND, ErrorCode.REPORT_NOT_FOUND.getMessage() + " id=" + reportId);
    }
}
