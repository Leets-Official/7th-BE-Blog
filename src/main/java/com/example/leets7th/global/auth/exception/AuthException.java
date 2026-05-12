package com.example.leets7th.global.auth.exception;

import com.example.leets7th.global.apiPayload.code.BaseCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;

public class AuthException extends GeneralException {
    public AuthException(BaseCode code) {
        super(code);
    }
}
