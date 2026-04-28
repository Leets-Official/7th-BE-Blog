package com.example.leets7th.domain.comment.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentSuccessCode implements BaseCode {
    CREATE_COMMENT_SUCCESS(HttpStatus.CREATED,
            "COMMENT201_1",
            "댓글 작성에 성공했습니다."),
    CREATE_REPLY_SUCCESS(HttpStatus.CREATED,
            "COMMENT201_2",
            "대댓글 작성에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
