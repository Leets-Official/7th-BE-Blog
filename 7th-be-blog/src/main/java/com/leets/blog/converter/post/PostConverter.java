package com.leets.blog.converter.post;

import com.leets.blog.dto.post.PostRequest;
import com.leets.blog.dto.post.PostResponse;
import com.leets.blog.entity.post.Post;
import com.leets.blog.entity.user.User;

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
