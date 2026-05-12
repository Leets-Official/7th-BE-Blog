package com.example.leets7th.domain.user.exception;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(BaseCode code) {
        super(code);
    }
}
