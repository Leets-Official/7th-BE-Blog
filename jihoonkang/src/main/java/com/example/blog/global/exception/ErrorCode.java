package com.example.blog.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "U002", "이미 사용 중인 이메일입니다."),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "게시글을 찾을 수 없습니다."),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "댓글을 찾을 수 없습니다."),

    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "A001", "접근 권한이 없습니다."),

    INVALID_REPORT_TARGET(HttpStatus.BAD_REQUEST, "R001", "유효하지 않은 신고 대상입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
