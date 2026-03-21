package com.example.leets7th.global.apiPayload.handler;

import com.example.leets7th.global.apiPayload.ApiResponse;
import com.example.leets7th.global.apiPayload.code.BaseCode;
import com.example.leets7th.global.apiPayload.code.GeneralErrorCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionAdvice {
    // 서비스 로직에서 의도적으로 발생시키는 예외
    // GeneralException을 상속한 모든 커스텀 예외 처리
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(
            GeneralException ex
    ) {
        log.warn("GeneralException occurred: code={}, message={}",
                ex.getCode().getCode(), ex.getCode().getMessage(), ex);
        return ResponseEntity.status(ex.getCode().getStatus())
                .body(ApiResponse.onFailure(
                                ex.getCode(),
                                null
                        )
                );
    }

    // 예상하지 못한 서버 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(
            Exception ex
    ) {
        log.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
        BaseCode code = GeneralErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.onFailure(
                        code,
                        null
                ));
    }
}
