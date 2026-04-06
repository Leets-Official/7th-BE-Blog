package com.example.leets7th.domain.post.dto.res;


import lombok.Builder;

import java.time.LocalDateTime;

public class PostResponseDTO {
    @Builder
    public record PostListResDTO (
            Long postId,
            String title,
            String nickname,
            LocalDateTime createdAt, // 생성일자 추가
            LocalDateTime updatedAt
    ){}
}
