package com.example.leets_7th.common.status;

import com.example.leets_7th.common.base.BaseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseStatus {

    // test
    HEALTH_CHECK_SUCCESS_STATUS(HttpStatus.OK, "TEST_200", "OK"),
    STRING_REPEAT_SUCCESS(HttpStatus.CREATED, "TEST_201", "문자열을 성공적으로 출력했습니다."),

    // User
    CREATE_USER_SUCCESS(HttpStatus.CREATED,"AUTH_2011","회원가입에 성공했습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,"AUTH_2001","로그인에 성공했습니다."),
    LOGOUT_SUCCESS(HttpStatus.OK,"AUTH_2002","로그아웃에 성공했습니다."),
    REISSUE_SUCCESS(HttpStatus.OK,"AUTH_2003","리프레쉬 토큰 재발급에 성공했습니다."),

    // Post
    GET_ALL_POST_SUCCESS(HttpStatus.OK, "POST_2001", "게시글 목록 조회에 성공했습니다."),
    GET_POST_DETAIL_SUCCESS(HttpStatus.OK, "POST_2002", "게시글 상세 조회에 성공했습니다."),
    CREATE_POST_SUCCESS(HttpStatus.CREATED, "POST_2011", "게시글 생성에 성공했습니다."),
    UPDATE_POST_SUCCESS(HttpStatus.CREATED, "POST_2003", "게시글 수정에 성공했습니다."),
    DELETE_POST_SUCCESS(HttpStatus.OK, "POST_2004", "게시글 삭제에 성공했습니다."),
    LIKE_POST_SUCCESS(HttpStatus.OK,"POST_2005","게시글 좋아요에 성공했습니다."),
    UNLIKE_POST_CANCEL_SUCCESS(HttpStatus.OK,"POST_2006","게시글 좋아요 취소에 성공했습니다."),
    REPORT_POST_SUCCESS(HttpStatus.OK,"POST_2007","게시글 신고에 성공했습니다."),

    // Comment
    CREATE_COMMENT_SUCCESS(HttpStatus.CREATED,"COMM_2011","댓글 생성에 성공했습니다."),
    UPDATE_COMMENT_SUCCESS(HttpStatus.CREATED, "COMM_2003", "댓글 수정에 성공했습니다."),
    DELETE_COMMENT_SUCCESS(HttpStatus.OK, "COMM_2004", "댓글 삭제에 성공했습니다."),
    REPORT_COMMENT_SUCCESS(HttpStatus.OK,"COMM_REPORT_2001","댓글 신고에 성공했습니다."),
    LIKE_COMMENT_SUCCESS(HttpStatus.OK,"LIKE_2001","댓글 좋아요에 성공했습니다."),
    UNLIKE_COMMENT_CANCEL_SUCCESS(HttpStatus.OK,"UNLIKE_2001","댓글 좋아요 취소에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
