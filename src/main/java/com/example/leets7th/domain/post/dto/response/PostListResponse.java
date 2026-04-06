package com.example.leets7th.domain.post.dto.response;

import com.example.leets7th.domain.post.entity.Post;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record PostListResponse(
        List<PostSummaryDto> posts,
        PageInfoDto pageInfo
) {
    public record PostSummaryDto(
            Long postId,
            String title,
            String content,
            String thumbnailImageUrl,
            String author,
            LocalDateTime createdAt
    ) {
        public static PostSummaryDto from(Post post) {
            String nickname = post.getUser().getNickname();
            return new PostSummaryDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getThumbnailImageUrl(),
                    nickname != null ? nickname : "Unknown",
                    post.getCreatedAt()
            );
        }
    }

    public record PageInfoDto(
            int page,
            int size,
            long totalElements,
            int totalPages,
            boolean hasNext
    ) {
    }

    public static PostListResponse from(Page<Post> page) {
        List<PostSummaryDto> posts = page.getContent().stream()
                .map(PostSummaryDto::from)
                .toList();

        PageInfoDto pageInfo = new PageInfoDto(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );

        return new PostListResponse(posts, pageInfo);
    }
}
