package com.leets.blog.global.exception;

import com.leets.blog.global.common.ApiResponse;
import com.leets.blog.global.common.BaseErrorCode;
import com.leets.blog.global.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 커스텀 예외 처리 (GeneralException)
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleGeneralException(GeneralException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode.getCode(), e.getMessage());
        
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errorResponse));
    }

    // 2. @Valid 검증 실패 시
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        
        ErrorResponse errorResponse = ErrorResponse.of(BaseErrorCode.BAD_REQUEST.getCode(), errorMessage);
        
        return ResponseEntity
                .status(BaseErrorCode.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.onFailure(BaseErrorCode.BAD_REQUEST.getCode(), "입력값 검증에 실패하였습니다.", errorResponse));
    }

    // 2. 일반적인 런타임 예외 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException occurred: ", e);
        ErrorResponse errorResponse = ErrorResponse.of(BaseErrorCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
        
        return ResponseEntity
                .status(BaseErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.onFailure(BaseErrorCode.INTERNAL_SERVER_ERROR.getCode(), "서버 내부 오류가 발생했습니다.", errorResponse));
    }

    // 3. 최상위 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleException(Exception e) {
        log.error("Unhandled Exception occurred: ", e);
        ErrorResponse errorResponse = ErrorResponse.of(BaseErrorCode.INTERNAL_SERVER_ERROR.getCode(), "예상치 못한 서버 오류가 발생했습니다.");
        
        return ResponseEntity
                .status(BaseErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.onFailure(BaseErrorCode.INTERNAL_SERVER_ERROR.getCode(), "서버 내부 오류가 발생했습니다.", errorResponse));
    }
}
