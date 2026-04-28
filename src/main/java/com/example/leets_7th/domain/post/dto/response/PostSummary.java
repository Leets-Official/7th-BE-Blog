package com.example.leets_7th.domain.post.dto.response;

import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.enums.PostVisibility;

import java.time.LocalDateTime;

public record PostSummary(
        Long postId,
        String title,
        String thumbnailImageUrl,
        String author,
        Long likeCount,
        PostVisibility postVisibility,
        LocalDateTime createdAt
) {
    public static PostSummary from(Post post) {
        return new PostSummary(
                post.getId(),
                post.getTitle(),
                post.getThumbnailImageUrl(),
                post.getUser().getName(),
                post.getLikeCount(),
                post.getPostVisibility(),
                post.getCreatedAt()
        );
    }
}
