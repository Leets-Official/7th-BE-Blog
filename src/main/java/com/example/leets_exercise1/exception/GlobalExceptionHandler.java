package com.example.leets_exercise1.exception;

import com.example.leets_exercise1.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import com.example.leets_exercise1.common.response.ApiResponse;
import com.example.leets_exercise1.exception.CommentNotFoundException;
import com.example.leets_exercise1.exception.DuplicateReportException;
import com.example.leets_exercise1.exception.InvalidReportStateException;
import com.example.leets_exercise1.exception.ReportNotFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail("4002", "잘못된 요청입니다.", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("request", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail("4001", "잘못된 요청입니다.", errors));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handlePostNotFoundException(PostNotFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("4040", e.getMessage(), result));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleUserNotFoundException(UserNotFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("4041", e.getMessage(), result));
    }

    @ExceptionHandler(DuplicateReportException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleDuplicateReportException(DuplicateReportException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail("4090", e.getMessage(), result));
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleCommentNotFoundException(CommentNotFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("4042", e.getMessage(), result));
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleReportNotFoundException(ReportNotFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("4043", e.getMessage(), result));
    }

    @ExceptionHandler(InvalidReportStateException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleInvalidReportStateException(InvalidReportStateException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail("4004", e.getMessage(), result));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleNoResourceFoundException(NoResourceFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("path", e.getResourcePath());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("4044", "요청한 경로를 찾을 수 없습니다.", result));
    }
}