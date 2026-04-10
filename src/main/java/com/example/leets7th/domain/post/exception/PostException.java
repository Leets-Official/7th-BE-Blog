package com.example.leets7th.domain.post.exception;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;

public class PostException extends GeneralException {
    public PostException(BaseCode code) {
        super(code);
    }
}
