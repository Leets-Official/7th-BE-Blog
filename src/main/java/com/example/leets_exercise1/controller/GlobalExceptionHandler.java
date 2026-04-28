package com.example.leets_exercise1.controller;

import com.example.leets_exercise1.common.response.ApiResponse;
import com.example.leets_exercise1.exception.CommentNotFoundException;
import com.example.leets_exercise1.exception.DuplicateReportException;
import com.example.leets_exercise1.exception.InvalidReportStateException;
import com.example.leets_exercise1.exception.PostNotFoundException;
import com.example.leets_exercise1.exception.ReportNotFoundException;
import com.example.leets_exercise1.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleJsonException(HttpMessageNotReadableException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "잘못된 JSON 요청입니다.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail("4003", "잘못된 JSON 요청입니다.", result));
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleException(Exception e) {
        e.printStackTrace();

        Map<String, Object> result = new HashMap<>();
        result.put("message", "서버 내부 오류가 발생했습니다.");
        result.put("errorType", e.getClass().getSimpleName());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail("5000", "서버 내부 오류가 발생했습니다.", result));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleNoResourceFoundException(NoResourceFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("path", e.getResourcePath());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("4044", "요청한 경로를 찾을 수 없습니다.", result));
    }
}