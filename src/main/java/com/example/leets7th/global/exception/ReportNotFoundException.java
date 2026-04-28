package com.example.leets7th.global.exception;

public class ReportNotFoundException extends RuntimeException {

    public ReportNotFoundException(Long reportId) {
        super("해당 신고를 찾을 수 없습니다. id=" + reportId);
    }
}
