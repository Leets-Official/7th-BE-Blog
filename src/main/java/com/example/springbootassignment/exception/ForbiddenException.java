package com.example.springbootassignment.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(Long postId, Long userId) {
        super("게시글 id: " + postId + "를(을) 수정/삭제할 권한이 없습니다. (사용자 id: " + userId + ")");
    }
}
