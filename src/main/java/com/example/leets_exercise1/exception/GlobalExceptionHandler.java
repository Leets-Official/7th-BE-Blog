package com.example.leets_exercise1.exception;

import com.example.leets_exercise1.auth.exception.EmailAlreadyExistsException;
import com.example.leets_exercise1.auth.exception.InvalidLoginException;
import com.example.leets_exercise1.auth.exception.NicknameAlreadyExistsException;
import com.example.leets_exercise1.auth.exception.RefreshTokenNotFoundException;
import com.example.leets_exercise1.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("request", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail("4001", "잘못된 요청입니다.", errors));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handlePostNotFoundException(PostNotFoundException e) {
        return error(HttpStatus.NOT_FOUND, "4040", e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleUserNotFoundException(UserNotFoundException e) {
        return error(HttpStatus.NOT_FOUND, "4041", e.getMessage());
    }

    @ExceptionHandler(DuplicateReportException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleDuplicateReportException(DuplicateReportException e) {
        return error(HttpStatus.CONFLICT, "4090", e.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleCommentNotFoundException(CommentNotFoundException e) {
        return error(HttpStatus.NOT_FOUND, "4042", e.getMessage());
    }

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleReportNotFoundException(ReportNotFoundException e) {
        return error(HttpStatus.NOT_FOUND, "4043", e.getMessage());
    }

    @ExceptionHandler(InvalidReportStateException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleInvalidReportStateException(InvalidReportStateException e) {
        return error(HttpStatus.BAD_REQUEST, "4004", e.getMessage());
    }

    @ExceptionHandler({EmailAlreadyExistsException.class, NicknameAlreadyExistsException.class})
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleAuthConflictException(RuntimeException e) {
        return error(HttpStatus.CONFLICT, "4091", e.getMessage());
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleInvalidLoginException(InvalidLoginException e) {
        return error(HttpStatus.UNAUTHORIZED, "4010", e.getMessage());
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e) {
        return error(HttpStatus.UNAUTHORIZED, "4011", e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleNoResourceFoundException(NoResourceFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("path", e.getResourcePath());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("4044", "요청한 경로를 찾을 수 없습니다.", result));
    }

    private ResponseEntity<ApiResponse<Map<String, Object>>> error(HttpStatus status, String code, String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", message);

        return ResponseEntity.status(status)
                .body(ApiResponse.fail(code, message, result));
    }
}
