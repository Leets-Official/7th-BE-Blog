package com.example.leets_7th.domain.comment.entity;

import com.example.leets_7th.common.base.BaseEntity;
import com.example.leets_7th.common.exception.GeneralException;
import com.example.leets_7th.common.status.ErrorStatus;
import com.example.leets_7th.domain.comment.enums.CommentStatus;
import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "comment")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Integer likeCount = 0;

    @Column(nullable = false)
    private Integer reportCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentStatus status = CommentStatus.ACTIVE;

    @Builder
    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public void delete() {
        this.status = CommentStatus.DELETED;
    }

    public void update(String content) {
        this.content = content;
    }

    public void hideByReports() {
        this.status = CommentStatus.HIDDEN;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount <= 0) {
            throw new GeneralException(ErrorStatus.COMMENT_LIKE_COUNT_INVALID);
        }
        this.likeCount--;
    }
}
