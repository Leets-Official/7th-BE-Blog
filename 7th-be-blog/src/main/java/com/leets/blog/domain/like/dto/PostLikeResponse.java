package com.leets.blog.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostLikeResponse {

    private Long likeId;
    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;
}
