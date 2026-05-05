package com.example.leets_7th.domain.post.entity;

import com.example.leets_7th.common.base.BaseEntity;
import com.example.leets_7th.domain.comment.entity.Comment;
import com.example.leets_7th.domain.post.enums.PostStatus;
import com.example.leets_7th.domain.post.enums.PostVisibility;
import com.example.leets_7th.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "post")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_visibility", nullable = false)
    private PostVisibility postVisibility;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @Column(name = "thumbnail")
    private String thumbnailImageUrl;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    private Post(
            User user,
            String title,
            String content,
            String thumbnailImageUrl,
            PostVisibility postVisibility,
            Long likeCount,
            PostStatus status
    ) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.postVisibility = postVisibility;
        this.likeCount = likeCount;
        this.status = status;
    }

    public static Post create(
            User user,
            String title,
            String content,
            String thumbnailImageUrl,
            PostVisibility postVisibility
    ) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .thumbnailImageUrl(thumbnailImageUrl)
                .postVisibility(postVisibility != null ? postVisibility : PostVisibility.PUBLIC)
                .likeCount(0L)
                .status(PostStatus.ACTIVE)
                .build();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    public void hide() {
        this.status = PostStatus.HIDDEN;
    }

    public void delete() {
        this.status = PostStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
    }

}
