package com.example.demo.post.dto;

import com.example.demo.post.entity.BlockType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "게시글 블록 DTO (텍스트 또는 이미지 블록)")
public class PostBlockDto {

    @Schema(
            description = "블록 타입",
            example = "TEXT"
    )
    private BlockType blockType;

    @Schema(
            description = "텍스트 내용 (blockType이 TEXT일 때 사용)",
            example = "오늘 자취방 리뷰 남깁니다."
    )
    private String textContent;

    @Schema(
            description = "미디어 ID (blockType이 IMAGE일 때 사용)",
            example = "3"
    )
    private Long mediaId;
}
