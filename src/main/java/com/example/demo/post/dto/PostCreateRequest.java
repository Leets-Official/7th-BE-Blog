package com.example.demo.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

@Getter
@Schema(description = "게시글 생성 요청 DTO")
public class PostCreateRequest {

    @Schema(description = "게시글 제목", example = "학교 근처 자취방 후기")
    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    private String title;

    @Schema(description = "게시글 설명", example = "학교 근처 원룸에 대한 리뷰입니다.")
    @NotBlank(message = "설명은 비어 있을 수 없습니다.")
    private String description;

    @Schema(
            description = "게시글 블록 목록 (TEXT 또는 IMAGE 블록으로 구성, 최소 1개 이상)",
            required = true
    )
    @NotEmpty(message = "블록은 최소 1개 이상이어야 합니다.")
    private List<PostBlockDto> blocks;
}