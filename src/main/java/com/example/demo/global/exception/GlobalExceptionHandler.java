package com.example.demo.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity.badRequest()
                .body(ResponseUtil.fail(BaseCode.INVALID_REQUEST, errors));
    }

    // 게시글 없음
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleIllegal(
            IllegalArgumentException e) {

        if (e.getMessage().contains("게시글 없음")) {
            return ResponseEntity.status(404)
                    .body(ResponseUtil.fail(
                            BaseCode.POST_NOT_FOUND,
                            Map.of("postId", -1)
                    ));
        }

        return ResponseEntity.badRequest()
                .body(ResponseUtil.fail(BaseCode.INVALID_REQUEST, null));
    }

    // 기타
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException() {
        return ResponseEntity.internalServerError()
                .body(ResponseUtil.fail(BaseCode.INVALID_REQUEST, null));
    }
}
