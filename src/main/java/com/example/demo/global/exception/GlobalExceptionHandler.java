package com.example.demo.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity.badRequest()
                .body(ResponseUtil.fail(BaseCode.INVALID_REQUEST, errors));
    }

    // CustomException 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(
            CustomException e
    ) {
        return ResponseEntity.badRequest()
                .body(ResponseUtil.fail(e.getBaseCode(), null));
    }

    // IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegal(
            IllegalArgumentException e
    ) {
        return ResponseEntity.badRequest()
                .body(ResponseUtil.fail(BaseCode.INVALID_REQUEST, e.getMessage()));
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(
            Exception e
    ) {
        return ResponseEntity.internalServerError()
                .body(ResponseUtil.fail(BaseCode.INVALID_REQUEST, e.getMessage()));
    }
}
