package com.example.week2.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {

    POST_CREATED(HttpStatus.CREATED,
            "POST201_1","게시글 작성에 성공하였습니다."),
    POST_UPDATED(HttpStatus.OK,
            "POST200_3","게시글이 수정되었습니다."),
    POST_DETAIL_GET(HttpStatus.OK,
            "POST200_2","게시글 상세 조회에 성공하였습니다."),
    POST_GET(HttpStatus.OK,
            "POST200_1","게시글 목록 조회에 성공하였습니다."),
    POST_DELETED(HttpStatus.OK,
            "POST200_4", "게시글 삭제에 성공하였습니다."),
    COMMENT_CREATED(HttpStatus.CREATED,
            "COMMENT201_1","댓글 작성에 성공하였습니다."),
    COMMENT_GET(HttpStatus.OK,
            "COMMENT200_2","댓글 조회에 성공하였습니다."),
    COMMENT_LIKED(HttpStatus.OK,
            "COMMENT200_3","댓글 좋아요에 성공하였습니다."),
    REPORT_COMMENT_CREATED(HttpStatus.CREATED,
            "REPORT_COMMENT201_1","댓글 신고에 성공하였습니다."),
    REPORT_COMMENT_RESOLVED(HttpStatus.OK,
            "REPORT_COMMENT200_1", "댓글 신고처리에 성공하였습니다.");



    private final HttpStatus status;
    private final String code;
    private final String message;

    SuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}