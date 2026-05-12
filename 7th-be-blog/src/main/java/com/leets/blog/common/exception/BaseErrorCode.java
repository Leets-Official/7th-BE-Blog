package com.leets.blog.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BaseErrorCode {
    SUCCESS(HttpStatus.OK, "COMMON200", "요청에 성공하였습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 내부 오류가 발생했습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER4002", "이미 등록된 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "USER4003", "이미 등록된 닉네임입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST4001", "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT4001", "댓글을 찾을 수 없습니다."),
    DUPLICATE_REPORT(HttpStatus.CONFLICT, "REPORT4001", "이미 신고를 완료했습니다."),
    REPORT_ALREADY_RESOLVED(HttpStatus.CONFLICT, "REPORT4002", "이미 처리 완료된 신고입니다."),
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT4003", "신고를 찾을 수 없습니다."),
    DUPLICATE_POST_LIKE(HttpStatus.CONFLICT, "LIKE4001", "이미 좋아요한 게시글입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
