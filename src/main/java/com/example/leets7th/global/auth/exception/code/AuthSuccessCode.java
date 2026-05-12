package com.example.leets7th.global.auth.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthSuccessCode implements BaseCode {
    SIGNUP_SUCCESS(HttpStatus.CREATED, "AUTH201_1", "회원가입에 성공했습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, "AUTH200_1", "로그인에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK, "AUTH200_2", "로그아웃에 성공했습니다."),
    WITHDRAW_SUCCESS(HttpStatus.OK, "AUTH200_3", "회원탈퇴에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
