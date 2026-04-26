package com.example.week2.global.exception;

public class ForbiddenPostAccessException extends RuntimeException {

    public ForbiddenPostAccessException() {
        super("본인이 작성한 게시글만 수정 또는 삭제할 수 있습니다.");
    }
}