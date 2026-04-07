package com.leets.blog.post.dto;

import com.leets.blog.post.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}