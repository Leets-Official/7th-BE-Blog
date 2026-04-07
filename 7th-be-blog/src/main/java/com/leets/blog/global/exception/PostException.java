package com.leets.blog.global.exception;

import com.leets.blog.global.common.BaseErrorCode;

public class PostException extends GeneralException {
    public PostException(BaseErrorCode errorCode) {
        super(errorCode);
    }

    public PostException(BaseErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
