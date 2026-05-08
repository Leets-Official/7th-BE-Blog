package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문자열 반복 요청")
public record RepeatRequest(
        @Schema(description = "반복할 문자열", example = "hello")
        String value
) {
}
