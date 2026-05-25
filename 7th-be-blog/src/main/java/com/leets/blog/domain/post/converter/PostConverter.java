package com.leets.blog.domain.post.converter;

import com.leets.blog.domain.post.dto.PostResponse;
import com.leets.blog.domain.post.entity.Post;

public class PostConverter {

    private PostConverter() {
    }

    public static PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
