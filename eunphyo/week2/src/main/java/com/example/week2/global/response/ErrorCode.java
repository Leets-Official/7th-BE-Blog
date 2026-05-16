package com.example.week2.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode implements BaseErrorCode{

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,
            "COMMON500_1", "예기치 않은 서버 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,
            "COMMON400_1", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,
            "AUTH401_1", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN,
            "AUTH403_1", "요청이 거부되었습니다."),


    //post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,
            "POST404_1", "존재하지 않는 게시글입니다."),

    //comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,
                      "COMMENT404_1", "존재하지 않는 댓글입니다."),
    COMMENT_ALREADY_LIKED(HttpStatus.BAD_REQUEST,
                          "COMMENT400_2", "이미 좋아요를 누른 댓글입니다."),

    //report
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND,
            "REPORT400_1", "존재하지 않는 신고입니다."),
    REPORT_COMMENT_ALREADY_RESOLVED(HttpStatus.CONFLICT,
                        "REPORT400_2", "이미 신고 처리된 댓글입니다."),

    //auth
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,
            "AUTH400_1", "비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,
            "AUTH401_2", "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(
            HttpStatus.UNAUTHORIZED,
            "AUTH401_2", "유효하지 않은 Refresh Token입니다."),

    //user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,
            "USER404_1", "존재하지 않는 사용자입니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT,
            "USER400_2", "이미 존재하는 이메일입니다."),
    NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT,
            "USER400_3", "이미 존재하는 닉네임입니다.");



    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}