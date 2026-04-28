package com.example.leets7th.domain.comment.exception;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;

public class CommentException extends GeneralException {
    public CommentException(BaseCode code) {
        super(code);
    }
}
