package com.leets.blog.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "에러 응답")
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "POST4001")
    private final String code;

    @Schema(description = "에러 메시지", example = "게시글을 찾을 수 없습니다.")
    private final String message;

    @Schema(description = "에러 발생 시각", example = "2026-05-05T14:30:00")
    private final LocalDateTime timestamp;

    public static ErrorResponse of(String code, String message) {
        return ErrorResponse.builder()
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
