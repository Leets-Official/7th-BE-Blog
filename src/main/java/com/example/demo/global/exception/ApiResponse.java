package com.example.demo.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private boolean isSuccess;
    private String code;
    private String message;
    private T result;
}
