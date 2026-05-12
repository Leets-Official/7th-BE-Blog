package com.example.leets7th.global.exception;

public class AlreadyResolvedException extends BusinessException {

    public AlreadyResolvedException() {
        super(ErrorCode.ALREADY_RESOLVED);
    }
}
