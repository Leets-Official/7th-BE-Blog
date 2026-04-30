package com.example.week2.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean isSuccess;
    private String code;
    private String message;
    private T result;

    public static <T> ApiResponse<T> success(String code, String message, T result) {
        return new ApiResponse<>(true, code, message, result);
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode, T result) {
        return new ApiResponse<>(
                true,
                successCode.getCode(),
                successCode.getMessage(),
                result
        );
    }
}
