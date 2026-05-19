package com.example.leets_project.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    // USER
    USER_CREATE(HttpStatus.CREATED, "USER_2010", "유저 생성 성공"),
    // POST
    POST_LIST(HttpStatus.OK, "POST_2000", "게시글 목록 조회 성공"),
    POST_DETAIL(HttpStatus.OK, "POST_2001", "게시글 조회 성공"),
    POST_UPDATE(HttpStatus.OK, "POST_2002", "게시글 수정 성공"),
    POST_DELETE(HttpStatus.OK, "POST_2003", "게시글 삭제 성공"),
    POST_HIDE(HttpStatus.OK, "POST_2004", "게시글 숨김 성공"),
    POST_RESTORE(HttpStatus.OK, "POST_2005", "게시글 복구 성공"),
    POST_CREATE(HttpStatus.CREATED, "POST_2010", "게시글 생성 성공"),
    // COMMENT
    COMMENT_CREATE(HttpStatus.CREATED, "COMMENT_2010", "댓글 생성 성공"),
    COMMENT_LIST(HttpStatus.OK, "COMMENT_2000", "댓글 목록 조회 성공"),
    COMMENT_UPDATE(HttpStatus.OK, "COMMENT_2001", "댓글 수정 성공"),
    COMMENT_DELETE(HttpStatus.OK, "COMMENT_2002", "댓글 삭제 성공"),
    COMMENT_HIDE(HttpStatus.OK, "COMMENT_2004", "댓글 숨김 성공"),
    COMMENT_RESTORE(HttpStatus.OK, "COMMENT_2005", "댓글 복구 성공"),
    // REPORT
    REPORT_POST(HttpStatus.CREATED, "REPORT_2010", "게시글 신고 성공"),
    REPORT_COMMENT(HttpStatus.CREATED, "REPORT_2011", "댓글 신고 성공"),
    REPORT_RESOLVE(HttpStatus.OK, "REPORT_2001", "신고 처리 완료"),
    // AUTH
    AUTH_LOGIN(HttpStatus.OK, "AUTH_2000", "로그인 성공"),
    AUTH_REISSUE(HttpStatus.OK, "AUTH_2001", "토큰 재발급 성공"),
    AUTH_LOGOUT(HttpStatus.OK, "AUTH_2002", "로그아웃 성공");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
