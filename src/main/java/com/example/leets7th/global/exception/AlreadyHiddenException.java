package com.example.leets7th.global.exception;

public class AlreadyHiddenException extends RuntimeException {

    public AlreadyHiddenException() {
        super("이미 숨김 처리된 게시글입니다.");
    }
}
