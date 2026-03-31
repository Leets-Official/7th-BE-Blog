package com.example.leets7th.domain.post.entity;

import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.comment.entity.Comment;
import com.example.leets7th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostImage> images = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }
}