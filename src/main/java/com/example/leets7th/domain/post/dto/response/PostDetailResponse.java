package com.example.leets7th.domain.post.dto.response;

import com.example.leets7th.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponse(
        Long postId,
        String title,
        String content,
        String thumbnailImageUrl,
        CategoryDto category,
        AuthorDto author,
        List<ImageDto> images,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record CategoryDto(Long categoryId, String name) {
    }

    public record AuthorDto(Long userId, String nickname) {
    }

    public record ImageDto(Long imageId, String imageUrl) {
    }

    public static PostDetailResponse from(Post post) {
        CategoryDto categoryDto = null;
        if (post.getCategory() != null) {
            categoryDto = new CategoryDto(post.getCategory().getId(), post.getCategory().getName());
        }

        String nickname = post.getUser().getNickname();
        AuthorDto authorDto = new AuthorDto(
                post.getUser().getId(),
                nickname != null ? nickname : "Unknown"
        );

        List<ImageDto> images = post.getImages().stream()
                .map(img -> new ImageDto(img.getId(), img.getImageUrl()))
                .toList();

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getThumbnailImageUrl(),
                categoryDto,
                authorDto,
                images,
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
