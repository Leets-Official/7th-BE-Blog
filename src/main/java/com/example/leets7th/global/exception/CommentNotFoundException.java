package com.example.leets7th.global.exception;

public class CommentNotFoundException extends BusinessException {

    public CommentNotFoundException(Long commentId) {
        super(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage() + " id=" + commentId);
    }
}
