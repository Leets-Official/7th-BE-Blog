package com.example.demo.global.exception;

import com.example.demo.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Hidden
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ApiResponse.fail(e.getCode(), e.getMessage(), null));
    }

    @Hidden
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(
                        "INVALID_PARAMETER",
                        "잘못된 요청입니다.",
                        buildValidationErrorData(e.getBindingResult().getFieldErrors())
                ));
    }

    @Hidden
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleBindException(BindException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail(
                        "INVALID_PARAMETER",
                        "잘못된 요청입니다.",
                        buildValidationErrorData(e.getBindingResult().getFieldErrors())
                ));
    }

    @Hidden
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.fail("INVALID_PARAMETER", "잘못된 요청입니다.", null));
    }

    @Hidden
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(ApiResponse.fail("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.", null));
    }

    private Map<String, Object> toErrorMap(FieldError fieldError) {
        Map<String, Object> error = new HashMap<>();
        error.put("field", fieldError.getField());
        error.put("value", fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue());
        error.put("reason", fieldError.getDefaultMessage());
        return error;
    }

    private Map<String, Object> buildValidationErrorData(List<FieldError> fieldErrors) {
        List<Map<String, Object>> errors = fieldErrors.stream()
                .map(this::toErrorMap)
                .toList();

        Map<String, Object> data = new HashMap<>();
        data.put("errors", errors);
        return data;
    }
}
