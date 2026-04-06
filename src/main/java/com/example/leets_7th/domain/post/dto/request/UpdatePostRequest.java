package com.example.leets_7th.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdatePostRequest(

        @NotBlank
        @Schema(example = "제목")
        String title,

        @NotBlank
        @Schema(example = "내용")
        String content,

        Integer thumbnailIndex
) {
}
