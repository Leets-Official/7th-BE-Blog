package com.example.leets7th.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 수정 요청 (null인 필드는 수정하지 않음)")
public record PostUpdateRequest(
        @Schema(description = "수정할 제목", example = "수정된 제목") String title,
        @Schema(description = "수정할 내용", example = "수정된 내용입니다.") String content
) {
}
