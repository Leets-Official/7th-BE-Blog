package com.example.leets7th.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // 400
    INVALID_COMMENT_POST(HttpStatus.BAD_REQUEST, "INVALID_COMMENT_POST", "해당 게시글에 속한 댓글이 아닙니다."),

    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "접근 권한이 없습니다."),

    // 404
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "해당 댓글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY_NOT_FOUND", "해당 카테고리를 찾을 수 없습니다."),
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT_NOT_FOUND", "해당 신고를 찾을 수 없습니다."),

    // 409
    ALREADY_ADOPTED(HttpStatus.CONFLICT, "ALREADY_ADOPTED", "이미 채택된 댓글이 존재합니다."),
    ALREADY_HIDDEN(HttpStatus.CONFLICT, "ALREADY_HIDDEN", "이미 숨김 처리된 게시글입니다."),
    ALREADY_RESOLVED(HttpStatus.CONFLICT, "ALREADY_RESOLVED", "이미 처리된 신고입니다."),
    DUPLICATE_REPORT(HttpStatus.CONFLICT, "DUPLICATE_REPORT", "이미 신고한 대상입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
