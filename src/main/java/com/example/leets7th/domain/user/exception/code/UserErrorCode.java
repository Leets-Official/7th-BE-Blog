package com.example.leets7th.domain.user.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseCode {
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "USER400_1", "이미 사용 중인 이메일입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "USER401_1", "이메일 또는 비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404_1", "사용자를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
