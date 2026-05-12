package com.example.leets_exercise1.auth.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException() {
        super("유효한 refresh token을 찾을 수 없습니다.");
    }
}
