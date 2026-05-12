package com.example.leets7th.domain.user.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserSuccessCode implements BaseCode {
    SIGNUP_SUCCESS(HttpStatus.CREATED, "USER201_1", "회원가입에 성공했습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "USER200_1", "로그인에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "USER200_2", "로그아웃에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
