package com.leets.blog.domain.post.converter;

import com.leets.blog.domain.post.dto.PostRequest;
import com.leets.blog.domain.post.dto.PostResponse;
import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.user.entity.User;

public class PostConverter {
    
    // Request -> Entity
    public static Post toPost(PostRequest request, User user) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .isDeleted(false)
                .build();
    }

    // Entity -> Response
    public static PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
