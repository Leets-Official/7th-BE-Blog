package com.example.leets_7th.common.status;

import com.example.leets_7th.common.base.BaseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseStatus {

    // 예시
    COMM_ERROR_STATUS(HttpStatus.BAD_REQUEST, "COMM_400", "잘못된 요청입니다."),

    /**
     * Common
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMM_400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMM_401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMM_403", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMM_404", "요청한 자원을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMM_405", "허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMM_500", "서버 내부 오류입니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER_404","사용자를 찾을 수 없습니다."),


    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"POST_404","게시글이 존재하지 않습니다."),
    POST_ALREADY_DELETED(HttpStatus.BAD_REQUEST,"POST_400","이미 삭제된 게시글입니다."),
    ALREADY_LIKED_POST(HttpStatus.CONFLICT, "POST_409", "이미 좋아요를 누른 게시글입니다."),
    POST_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND,"POST_404","게시글에 좋아요 취소할 좋아요가 없습니다."),
    ALREADY_REPORTED_POST(HttpStatus.CONFLICT, "POST_409", "이미 신고한 게시글입니다."),


    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"COMM_4041","게시글 또는 댓글이 존재하지 않습니다."),
    ALREADY_LIKED_COMMENT(HttpStatus.BAD_REQUEST,"COMM_4001","이미 좋아요를 누른 댓글입니다."),
    FORBIDDEN_COMMENT_DELETE(HttpStatus.FORBIDDEN,"COMM_4031","댓글을 삭제할 권한이 없습니다."),
    FORBIDDEN_COMMENT_UPDATE(HttpStatus.FORBIDDEN,"COMM_4032","댓글을 수정할 권한이 없습니다."),
    COMMENT_LIKE_COUNT_INVALID(HttpStatus.BAD_REQUEST,"COMM_4002","좋아요 수는 0보다 작을 수 없습니다."),
    COMMENT_LIKE_NOT_FOUND(HttpStatus.NOT_FOUND,"COMM_4042","좋아요를 누르지 않은 댓글입니다."),
    ALREADY_REPORTED_COMMENT(HttpStatus.BAD_REQUEST,"COMM_4003","이미 신고한 댓글입니다."),
    DELETED_COMMENT(HttpStatus.NO_CONTENT,"COMM_2041","삭제된 댓글입니다."),
    HIDDEN_COMMENT(HttpStatus.NO_CONTENT,"COMM_2042","신고로 인해 숨겨진 댓글입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
