package com.example.leets_7th.domain.post.dto.request;

import com.example.leets_7th.domain.post.enums.PostVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record UpdatePostRequest(

        @Schema(example = "제목")
        @NotBlank(message = "제목은 공백일 수 없습니다")
        String title,

        @Schema(example = "내용")
        @NotBlank(message = "내용은 공백일 수 없습니다")
        String content,

        Integer thumbnailIndex,

        @Schema(
                description = "공개 범위 설정",
                allowableValues = {"PUBLIC", "FRIENDS", "PRIVATE"},
                example = "PUBLIC"
        )
        PostVisibility postVisibility

) {
}
