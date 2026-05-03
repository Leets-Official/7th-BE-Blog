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

    INVALID_REPORT_TARGET(HttpStatus.BAD_REQUEST, "R001", "유효하지 않은 신고 대상입니다."),
    ALREADY_REPORTED(HttpStatus.CONFLICT, "R002", "이미 신고한 대상입니다."),
    CANNOT_REPORT_SELF(HttpStatus.BAD_REQUEST, "R003", "자신의 게시물/댓글은 신고할 수 없습니다."),
    REPORT_ALREADY_RESOLVED(HttpStatus.CONFLICT, "R004", "이미 처리된 신고입니다."),

    POST_ALREADY_HIDDEN(HttpStatus.CONFLICT, "P002", "이미 숨김 처리된 게시물입니다."),

    INVALID_PARENT_COMMENT(HttpStatus.BAD_REQUEST, "C002", "유효하지 않은 부모 댓글입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
