package com.example.leets_project.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // COMMON
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "COMMON_4000", "유효하지 않은 값입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON_4001", "잘못된 입력입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_4002", "허용되지 않은 요청입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_5000", "서버 오류입니다."),

    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4040", "사용자를 찾을 수 없습니다."),
    USER_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_4001", "이미 사용 중인 이메일입니다."),
    USER_NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "USER_4002", "이미 사용 중인 닉네임입니다."),
    // POST
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_4040", "게시글을 찾을 수 없습니다."),
    POST_INVALID(HttpStatus.BAD_REQUEST, "POST_4001", "게시글 입력값이 올바르지 않습니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_4003", "작성자만 수정/삭제할 수 있습니다."),
    POST_HIDDEN(HttpStatus.FORBIDDEN, "POST_4030", "숨김 처리된 게시글입니다."),
    POST_DELETED(HttpStatus.NOT_FOUND, "POST_4031", "삭제된 게시글입니다."),
    POST_ALREADY_HIDDEN(HttpStatus.BAD_REQUEST, "POST_4090", "이미 숨김 처리된 게시글입니다."),
    POST_ALREADY_DELETED(HttpStatus.CONFLICT, "POST_4091", "이미 삭제 처리된 게시글입니다."),
    POST_ALREADY_ACTIVE(HttpStatus.CONFLICT, "POST_4092", "이미 활성화된 게시글입니다."),
    // COMMENT
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_4040", "댓글을 찾을 수 없습니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMENT_4003", "작성자만 수정/삭제할 수 있습니다."),
    COMMENT_HIDDEN(HttpStatus.FORBIDDEN, "COMMENT_4030", "숨김 처리된 댓글입니다."),
    COMMENT_DELETED(HttpStatus.NOT_FOUND, "COMMENT_4031", "삭제된 댓글입니다."),
    COMMENT_ALREADY_HIDDEN(HttpStatus.BAD_REQUEST, "COMMENT_4090", "이미 숨김 처리된 댓글입니다."),
    COMMENT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "COMMENT_4091", "이미 삭제 처리된 댓글입니다."),
    COMMENT_ALREADY_ACTIVE(HttpStatus.CONFLICT, "COMMENT_4007", "이미 활성화된 댓글입니다."),
    // REPORT
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT_4040", "신고를 찾을 수 없습니다."),
    REPORT_ALREADY_EXISTS(HttpStatus.CONFLICT, "REPORT_4090", "이미 신고한 대상입니다."),
    REPORT_ALREADY_RESOLVED(HttpStatus.CONFLICT, "REPORT_4091", "이미 처리된 신고입니다."),
    REPORT_CANNOT_SELF(HttpStatus.BAD_REQUEST, "REPORT_4001", "자신의 게시물/댓글은 신고할 수 없습니다."),
    // AUTH
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_4000", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH_4001", "접근 권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4002", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_4003", "만료된 토큰입니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "AUTH_4004", "토큰 타입이 올바르지 않습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_4005", "이메일 또는 비밀번호가 올바르지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_4040", "리프레시 토큰이 존재하지 않습니다."),
    // Kakao
    KAKAO_TOKEN_REQUEST_FAILED(HttpStatus.UNAUTHORIZED, "KAKAO_4000", "카카오 토큰 요청에 실패했습니다."),
    KAKAO_USER_INFO_REQUEST_FAILED(HttpStatus.UNAUTHORIZED, "KAKAO_4001", "카카오 사용자 정보 요청에 실패했습니다."),
    KAKAO_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "KAKAO_4002", "카카오 계정 이메일이 존재하지 않습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;

}
