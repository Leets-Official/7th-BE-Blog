package com.leets.blog.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "게시글 응답")
public class PostResponse {

    @Schema(description = "게시글 ID", example = "1")
    private Long id;
    @Schema(description = "게시글 제목", example = "스프링부트 Swagger 적용기")
    private String title;
    @Schema(description = "게시글 본문", example = "Swagger UI를 프로젝트에 적용한 과정을 정리합니다.")
    private String content;
    @Schema(description = "작성자 이름", example = "dongbin")
    private String authorName;
    @Schema(description = "생성 시각", example = "2026-05-05T14:30:00")
    private LocalDateTime createdAt;
}
