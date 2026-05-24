package com.leets.blog.post.domain;

import com.leets.blog.global.BaseTimeEntity;
import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.user.domain.User;
import com.leets.blog.comment.domain.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStatus status;

    @ManyToOne(fetch = FetchType.LAZY)      // 필요시만 가져오는 LAZY 지연로딩 사용
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void confirmUser(User user) {
        this.user = user;
        if (user != null && !user.getPosts().contains(this)) {
            user.getPosts().add(this);
        }
    }

    @Builder
    public Post(String title, String content, PostStatus status, User user) {
        this.title = title;
        this.content = content;
        this.status = status;
        if (user != null && !user.getPosts().contains(this)) {
            confirmUser(user);
        }
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void hide() {
        if (this.status == PostStatus.HIDDEN) {
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);
        }
        this.status = PostStatus.HIDDEN;
    }

    public void activate() {
        if (this.status == PostStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);
        }
        this.status = PostStatus.ACTIVE;
    }


}
