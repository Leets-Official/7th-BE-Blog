package com.example.demo.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "게시글 목록 응답")
public record PostListResponse(
        @Schema(description = "게시글 목록")
        List<PostListItemResponse> posts,
        @Schema(description = "페이지 정보")
        PageInfoResponse pageInfo
) {
}
