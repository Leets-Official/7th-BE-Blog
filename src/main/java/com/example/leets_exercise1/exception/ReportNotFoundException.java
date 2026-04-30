package com.example.leets_exercise1.exception;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException() {
        super("해당 신고를 찾을 수 없습니다.");
    }
}