package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문자열 반복 응답")
public record RepeatResponse(
        @Schema(description = "첫 번째 문자열", example = "hello")
        String string_one,
        @Schema(description = "두 번째 문자열", example = "hello")
        String string_two
) {
}
