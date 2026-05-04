package com.leets.blog.comment.domain;


import com.leets.blog.global.BaseTimeEntity;
import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.post.domain.Post;
import com.leets.blog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentStatus status;

    @Column(nullable = false)
    private boolean accepted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
        this.status = CommentStatus.ACTIVE;
        this.accepted = false;
    }

    public void hide() {
        if (this.status == CommentStatus.HIDDEN) {
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);
        }
        this.status = CommentStatus.HIDDEN;
    }

    public void activate() {
        if (this.status == CommentStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);
        }
        this.status = CommentStatus.ACTIVE;
    }

    public void accept() {
        if (this.accepted) {
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);
        }
        this.accepted = true;
    }
}
