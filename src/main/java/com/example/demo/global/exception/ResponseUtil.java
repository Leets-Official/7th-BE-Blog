package com.example.demo.global.exception;

public class ResponseUtil {

    public static <T> ApiResponse<T> success(BaseCode code, T result) {
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .code(code.getCode())
                .message(code.getMessage())
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> fail(BaseCode code, T result) {
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .code(code.getCode())
                .message(code.getMessage())
                .result(result)
                .build();
    }
}
