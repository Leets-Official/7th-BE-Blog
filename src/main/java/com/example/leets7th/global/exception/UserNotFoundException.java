package com.example.leets7th.global.exception;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long userId) {
        super(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage() + " id=" + userId);
    }
}
