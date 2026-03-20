package com.example.leets_7th.domain.test.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record RepeatRequest(
        @NotNull(message = "value는 필수입니다")
        @Schema(description = "value 값", example = "hello")
        String value
) {
}
