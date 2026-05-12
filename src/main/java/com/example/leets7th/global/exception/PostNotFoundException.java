package com.example.leets7th.global.exception;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException(Long postId) {
        super(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage() + " id=" + postId);
    }
}
