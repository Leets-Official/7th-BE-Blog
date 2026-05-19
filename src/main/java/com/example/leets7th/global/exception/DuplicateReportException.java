package com.example.leets7th.global.exception;

public class DuplicateReportException extends BusinessException {

    public DuplicateReportException() {
        super(ErrorCode.DUPLICATE_REPORT);
    }
}
