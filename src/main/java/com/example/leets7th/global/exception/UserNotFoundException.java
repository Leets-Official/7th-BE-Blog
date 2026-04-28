package com.example.leets7th.global.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("해당 사용자를 찾을 수 없습니다. id=" + userId);
    }
}
