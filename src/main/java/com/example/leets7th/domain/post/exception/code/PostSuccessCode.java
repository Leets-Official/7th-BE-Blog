package com.example.leets7th.domain.post.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostSuccessCode implements BaseCode {
    GET_POSTLIST_SUCCESS(HttpStatus.OK,
            "POST200_1",
            "게시글 리스트 조회에 성공했습니다."),
    GET_POST_SUCCESS(HttpStatus.OK,
            "POST200_2",
            "게시글 상세 조회에 성공했습니다."),
    CREAT_POST_SUCCESS(HttpStatus.CREATED,
            "POST201_3",
            "게시글 작성에 성공하였습니다."),
    PATCH_POST_SUCCESS(HttpStatus.OK,
            "POST200_3",
            "게시글 수정에 성공하였습니다."),
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
