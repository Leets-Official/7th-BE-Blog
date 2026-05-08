package com.example.demo.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "게시글 목록 조회 요청")
public record PostListRequest(
        @Schema(description = "페이지 번호", example = "0", defaultValue = "0")
        @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
        Integer page,

        @Schema(description = "페이지 크기", example = "10", defaultValue = "10")
        @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
        Integer size
) {
    public PostListRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}
