package com.leets.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        String message = e.getMessage();
        HttpStatus status = HttpStatus.NOT_FOUND; // 기본값은 404

        // 메시지에 '권한'이나 '작성자'가 포함되면 403으로 응답
        if (message.contains("권한") || message.contains("작성자")) {
            status = HttpStatus.FORBIDDEN;
        }

        return ResponseEntity.status(status)
                .body(Map.of(
                        "status", String.valueOf(status.value()),
                        "message", message
                ));
    }
}