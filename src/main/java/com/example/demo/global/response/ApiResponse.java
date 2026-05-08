package com.example.demo.global.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공통 API 응답 형식")
public record ApiResponse<T>(
        @Schema(description = "요청 성공 여부", example = "true")
        boolean success,
        @Schema(description = "응답 코드", example = "POST_CREATE_SUCCESS")
        String code,
        @Schema(description = "응답 메시지", example = "게시글 생성 성공")
        String message,
        @Schema(description = "응답 데이터")
        T data
) {
    public static <T> ApiResponse<T> success(String code, String message, T data) {
        return new ApiResponse<>(true, code, message, data);
    }

    public static <T> ApiResponse<T> fail(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }
}
