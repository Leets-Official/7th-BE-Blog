package com.example.week2.global.swagger;

import com.example.week2.global.response.BaseErrorCode;
import com.example.week2.global.response.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorCodeRegistry {

    private final Map<String, BaseErrorCode> errorCodeMap = new HashMap<>();

    public ErrorCodeRegistry() {
        register(ErrorCode.values());
    }

    private void register(BaseErrorCode[] errorCodes) {
        for (BaseErrorCode errorCode : errorCodes) {
            errorCodeMap.put(errorCode.getCode(), errorCode);
        }
    }

    public BaseErrorCode findByCode(String code) {
        return errorCodeMap.get(code);
    }
}