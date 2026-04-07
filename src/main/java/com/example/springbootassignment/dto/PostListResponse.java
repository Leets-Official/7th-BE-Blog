package com.example.springbootassignment.dto;

import com.example.springbootassignment.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponse {

    private List<Long> postId;
    private List<String> title;
    private List<String> nickname;
    private List<LocalDateTime> createdAt;

    /**
     * Page<Post>를 PostListResponse로 변환
     */
    public static PostListResponse from(Page<Post> posts) {
        PostListResponse response = new PostListResponse();
        response.postId = posts.getContent().stream()
                .map(Post::getId)
                .toList();
        response.title = posts.getContent().stream()
                .map(Post::getTitle)
                .toList();
        response.nickname = posts.getContent().stream()
                .map(post -> post.getUser().getNickname())
                .toList();
        response.createdAt = posts.getContent().stream()
                .map(Post::getCreatedAt)
                .toList();
        return response;
    }
}
