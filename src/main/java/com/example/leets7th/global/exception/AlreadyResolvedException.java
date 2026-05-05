package com.example.leets7th.global.exception;

public class AlreadyResolvedException extends RuntimeException {

    public AlreadyResolvedException() {
        super("이미 처리된 신고입니다.");
    }
}
