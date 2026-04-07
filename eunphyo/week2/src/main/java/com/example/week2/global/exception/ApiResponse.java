package com.example.week2.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean isSuccess;
    private String code;
    private String message;
    private T result;

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(true, "SUCCESS", "요청에 성공했습니다.", result);
    }

    public static ApiResponse<?> fail(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}