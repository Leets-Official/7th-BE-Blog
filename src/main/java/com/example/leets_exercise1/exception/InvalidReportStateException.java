package com.example.leets_exercise1.exception;

public class InvalidReportStateException extends RuntimeException {
    public InvalidReportStateException() {
        super("이미 처리 완료된 신고입니다.");
    }
}