package com.example.leets7th.domain.comment.exception.code;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements BaseCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,
            "COMMENT404_1",
            "해당 댓글이 존재하지 않습니다."),
    COMMENT_POST_MISMATCH(HttpStatus.BAD_REQUEST,
            "COMMENT400_2",
            "해당 댓글이 게시글에 속하지 않습니다."),
    REPLY_NOT_ALLOWED(HttpStatus.BAD_REQUEST,
            "COMMENT400_3",
            "대댓글에는 답글을 달 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
