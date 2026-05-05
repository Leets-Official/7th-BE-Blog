package com.example.leets7th.global.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(Long commentId) {
        super("해당 댓글을 찾을 수 없습니다. id=" + commentId);
    }
}
