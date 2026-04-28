package com.example.leets7th.domain.post.entity;

import com.example.leets7th.domain.category.entity.Category;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.comment.entity.Comment;
import com.example.leets7th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 썸네일 이미지 URL
    private String thumbnailImageUrl;

    // 카테고리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 댓글
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 이미지
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("imageOrder ASC")
    private List<PostImage> images = new ArrayList<>();

    // 게시글 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status = PostStatus.ACTIVE;

    public static Post create(String title, String content, String thumbnailImageUrl, User user, Category category) {
        Post post = new Post();
        post.title = title;
        post.content = content;
        post.thumbnailImageUrl = thumbnailImageUrl;
        post.user = user;
        post.category = category;
        post.status = PostStatus.ACTIVE;
        return post;
    }

    public void hide() {
        this.status = PostStatus.HIDDEN;
    }

    public void update(String title, String content) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }

    // 연관관계 편의 메서드

    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

    public void addImage(PostImage image) {
        images.add(image);
        image.setPost(this);
    }

    public void removeImage(PostImage image) {
        images.remove(image);
        image.setPost(null);
    }
}
