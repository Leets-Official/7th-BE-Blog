package com.example.demo.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "게시글 응답 DTO (생성/수정 결과)")
public class PostResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "학교 근처 자취방 후기")
    private String title;

    @Schema(
            description = "게시글 본문 내용 (텍스트 블록들을 합친 내용 또는 요약)",
            example = "오늘은 학교 근처 원룸 리뷰를 해보겠습니다."
    )
    private String content;

    @Schema(
            description = "게시글 설명 (간단 요약)",
            example = "학교 근처 원룸 리뷰입니다."
    )
    private String description;

    @Schema(description = "작성자 닉네임", example = "자취생")
    private String authorNickname;

    @Schema(description = "게시글 생성 시간", example = "2026-05-05T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "게시글 수정 시간", example = "2026-05-05T13:00:00")
    private LocalDateTime updatedAt;
}
