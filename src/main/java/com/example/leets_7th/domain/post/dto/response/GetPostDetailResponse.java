package com.example.leets_7th.domain.post.dto.response;

import com.example.leets_7th.domain.post.entity.Image;
import com.example.leets_7th.domain.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;

public record GetPostDetailResponse(
        Long postId,
        String title,
        String content,
        String thumbnailImageUrl,
        String author,
        List<String> imageUrls,
        LocalDateTime createdAt
) {
    public static GetPostDetailResponse from(Post post) {
        return new GetPostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getThumbnailImageUrl(),
                post.getUser().getName(),
                post.getImages().stream()
                        .map(Image::getImageUrl)
                        .toList(),
                post.getCreatedAt()
        );
    }
}
