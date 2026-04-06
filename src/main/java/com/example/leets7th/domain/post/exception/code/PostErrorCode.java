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
    POST_FORBIDDEN(HttpStatus.FORBIDDEN,
            "POST403_1",
            "수정 권한이 없습니다.")
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;
}
