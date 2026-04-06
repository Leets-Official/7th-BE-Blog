package com.example.demo.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailResponse {

    private Long postId;
    private String title;
    private String description;
    private String authorNickname;
    private LocalDateTime createdAt;

    private List<PostBlockResponse> blocks;
}
