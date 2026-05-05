package com.example.demo.post.dto;

import com.example.demo.post.entity.BlockType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "게시글 블록 응답 DTO")
public class PostBlockResponse {

    @Schema(
            description = "블록 타입 (TEXT: 텍스트, IMAGE: 이미지)",
            example = "TEXT"
    )
    private BlockType blockType;

    @Schema(
            description = "텍스트 내용 (blockType이 TEXT일 때 사용)",
            example = "오늘 자취방 리뷰 남깁니다."
    )
    private String textContent;

    @Schema(
            description = "이미지 URL (blockType이 IMAGE일 때 사용)",
            example = "http://localhost:8080/images/sample.jpg"
    )
    private String imageUrl;
}
