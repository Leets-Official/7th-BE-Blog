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

    GET_ALL_POST_SUCCESS(HttpStatus.OK, "POST_2001", "게시글 목록 조회에 성공했습니다."),
    GET_POST_DETAIL_SUCCESS(HttpStatus.OK, "POST_2002", "게시글 상세 조회에 성공했습니다."),
    CREATE_POST_SUCCESS(HttpStatus.CREATED, "POST_2011", "게시글 생성에 성공했습니다."),
    UPDATE_POST_SUCCESS(HttpStatus.CREATED, "POST_2003", "게시글 수정에 성공했습니다."),
    DELETE_POST_SUCCESS(HttpStatus.OK, "POST_2004", "게시글 삭제에 성공했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
