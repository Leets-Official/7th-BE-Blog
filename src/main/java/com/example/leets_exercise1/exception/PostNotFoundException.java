package com.example.leets_exercise1.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("해당 게시글을 찾을 수 없습니다.");
    }
}