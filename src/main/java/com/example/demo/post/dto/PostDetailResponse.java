package com.example.demo.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "게시글 상세 조회 응답 DTO")
public class PostDetailResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "학교 근처 자취방 후기")
    private String title;

    @Schema(description = "게시글 설명", example = "학교 근처 원룸에 대한 상세 리뷰입니다.")
    private String description;

    @Schema(description = "작성자 닉네임", example = "자취생")
    private String authorNickname;

    @Schema(description = "게시글 생성 시간", example = "2026-05-05T12:00:00")
    private LocalDateTime createdAt;

    @Schema(
            description = "게시글 블록 목록 (TEXT: textContent 사용, IMAGE: imageUrl 사용)"
    )
    private List<PostBlockResponse> blocks;
}
