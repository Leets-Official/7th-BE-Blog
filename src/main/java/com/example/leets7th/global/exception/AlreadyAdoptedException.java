package com.example.leets7th.global.exception;

public class AlreadyAdoptedException extends RuntimeException {

    public AlreadyAdoptedException() {
        super("이미 채택된 댓글이 존재합니다.");
    }
}
