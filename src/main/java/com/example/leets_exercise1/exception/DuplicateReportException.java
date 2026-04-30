package com.example.leets_exercise1.exception;

public class DuplicateReportException extends RuntimeException {
    public DuplicateReportException() {
        super("이미 신고한 대상입니다.");
    }
}