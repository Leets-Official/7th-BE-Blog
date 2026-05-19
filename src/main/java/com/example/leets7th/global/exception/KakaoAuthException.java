package com.example.leets7th.global.exception;

public class KakaoAuthException extends BusinessException {

    public KakaoAuthException() {
        super(ErrorCode.KAKAO_AUTH_FAILED);
    }
}
