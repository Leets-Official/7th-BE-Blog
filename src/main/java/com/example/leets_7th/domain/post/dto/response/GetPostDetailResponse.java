package com.example.leets_7th.domain.post.dto.response;

import com.example.leets_7th.domain.comment.dto.response.CommentResponse;
import com.example.leets_7th.domain.comment.enums.CommentStatus;
import com.example.leets_7th.domain.post.entity.Image;
import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.enums.PostVisibility;

import java.time.LocalDateTime;
import java.util.List;

public record GetPostDetailResponse(
        Long postId,
        String title,
        String content,
        String thumbnailImageUrl,
        String author,
        Long likeCount,
        PostVisibility postVisibility,
        List<String> imageUrls,
        List<CommentResponse> comments,
        LocalDateTime createdAt
) {
    public static GetPostDetailResponse from(Post post) {
        return new GetPostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getThumbnailImageUrl(),
                post.getUser().getName(),
                post.getLikeCount(),
                post.getPostVisibility(),
                post.getImages().stream()
                        .map(Image::getImageUrl)
                        .toList(),
                post.getComments().stream()
                        .filter(comment -> comment.getStatus() == CommentStatus.ACTIVE)
                        .map(CommentResponse::from)
                        .toList(),
                post.getCreatedAt()

        );
    }
}
