package com.example.leets7th.global.exception;

public class InvalidCommentPostException extends BusinessException {

    public InvalidCommentPostException() {
        super(ErrorCode.INVALID_COMMENT_POST);
    }
}
