package com.example.springbootassignment.dto;

import com.example.springbootassignment.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Post Entity를 PostResponse DTO로 변환
     */
    public static PostResponse from(Post post) {
        PostResponse response = new PostResponse();
        response.postId = post.getId();
        response.title = post.getTitle();
        response.content = post.getContent();
        response.createdAt = post.getCreatedAt();
        response.updatedAt = post.getUpdatedAt();
        return response;
    }
}
