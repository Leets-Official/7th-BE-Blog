package com.example.leets_exercise1.auth.exception;

public class KakaoLoginFailedException extends RuntimeException {
    public KakaoLoginFailedException() {
        super("카카오 로그인에 실패했습니다.");
    }
}
