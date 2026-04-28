package com.example.week2.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,
            "COMMON500_1", "예기치 않은 서버 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,
            "COMMON400_1", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,
            "AUTH401_1", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,
            "AUTH403_1", "요청이 거부되었습니다."),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST,
            "POST400_1", "존재하지 않는 게시글입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,
            "USER400_1", "존재하지 않는 사용자입니다."),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST,
                      "COMMENT400_1", "존재하지 않는 댓글입니다."),
    COMMENT_ALREADY_LIKED(HttpStatus.BAD_REQUEST,
                          "COMMENT400_2", "이미 좋아요를 누른 댓글입니다."),
    COMMENT_ALREADY_HIDDEN(HttpStatus.BAD_REQUEST,
                        "POST400_2", "이미 숨김 처리된 댓글입니다."),
    COMMENT_ALREADY_REPORTED(HttpStatus.BAD_REQUEST,
                        "POST400_2", "이미 신고 처리된 댓글입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}