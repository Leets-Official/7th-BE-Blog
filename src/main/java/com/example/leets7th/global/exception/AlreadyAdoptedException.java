package com.example.leets7th.global.exception;

public class AlreadyAdoptedException extends BusinessException {

    public AlreadyAdoptedException() {
        super(ErrorCode.ALREADY_ADOPTED);
    }
}
