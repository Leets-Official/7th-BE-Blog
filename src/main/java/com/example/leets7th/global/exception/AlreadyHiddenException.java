package com.example.leets7th.global.exception;

public class AlreadyHiddenException extends BusinessException {

    public AlreadyHiddenException() {
        super(ErrorCode.ALREADY_HIDDEN);
    }
}
