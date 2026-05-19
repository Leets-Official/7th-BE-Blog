package com.example.leets_exercise1.auth.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("이미 사용 중인 이메일입니다.");
    }
}
