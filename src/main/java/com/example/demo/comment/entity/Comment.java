package com.example.demo.comment.entity;

import com.example.demo.post.entity.Post;
import com.example.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setUser(User user) { this.user = user; }
    public void setPost(Post post) { this.post = post; }
    public void setContent(String c) { this.content = c; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
    public void setUpdatedAt(LocalDateTime t) { this.updatedAt = t; }
}
