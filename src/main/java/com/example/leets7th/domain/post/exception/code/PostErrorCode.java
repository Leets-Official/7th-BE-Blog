package com.example.leets7th.domain.post.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements BaseCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,
            "POST404_1",
            "해당 게시글이 존재하지 않습니다."),
    INPUT_NOT_FOUND(HttpStatus.BAD_REQUEST,
            "POST400_1",
            "제목과 내용을 입력해주세요."),
    TITLE_BAD_REQUEST(HttpStatus.BAD_REQUEST,
            "POST400_2",
            "제목은 최대 255자까지 가능합니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
