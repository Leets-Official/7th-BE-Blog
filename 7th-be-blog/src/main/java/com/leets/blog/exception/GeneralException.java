package com.leets.blog.exception;

import com.leets.blog.common.BaseErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    
    private final BaseErrorCode errorCode;

    public GeneralException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GeneralException(BaseErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
