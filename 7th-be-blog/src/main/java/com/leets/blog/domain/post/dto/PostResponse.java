package com.leets.blog.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
}
