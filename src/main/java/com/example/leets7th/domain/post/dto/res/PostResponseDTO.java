package com.example.leets7th.domain.post.dto.res;


import lombok.Builder;

public class PostResponseDTO {
    @Builder
    public record PostListResDTO (
            Long postId,
            String title,
            String nickname
    ){}
}
