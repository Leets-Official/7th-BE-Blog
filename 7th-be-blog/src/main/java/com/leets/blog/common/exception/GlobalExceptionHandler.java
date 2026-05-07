package com.leets.blog.common.exception;

import com.leets.blog.common.response.ApiResponse;
import com.leets.blog.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleGeneralException(GeneralException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), e.getMessage());

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode, errorCode.getMessage(), errorResponse));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = ErrorResponse.of(BaseErrorCode.BAD_REQUEST.getCode(), errorMessage);

        return ResponseEntity.status(BaseErrorCode.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.onFailure(BaseErrorCode.BAD_REQUEST, "입력값 검증에 실패하였습니다.", errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception e) {
        log.error("Unhandled exception", e);
        ErrorResponse errorResponse = ErrorResponse.of(
                BaseErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                BaseErrorCode.INTERNAL_SERVER_ERROR.getMessage()
        );

        return ResponseEntity.status(BaseErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.onFailure(BaseErrorCode.INTERNAL_SERVER_ERROR, errorResponse));
    }
}
