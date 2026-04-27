package com.leets.blog.global.common;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BaseResponse<T> {
    private final LocalDateTime timestamp;
    private final String message;
    private final T data;

    @Builder
    public BaseResponse(String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> ok(T data) {
        return BaseResponse.<T>builder()
                .message("요청이 성공적으로 처리되었습니다.")
                .data(data)
                .build();
    }
}