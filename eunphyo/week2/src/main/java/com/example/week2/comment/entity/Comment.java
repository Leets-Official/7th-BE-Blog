package com.example.week2.comment.entity;

import com.example.week2.global.entity.BaseEntity;
import com.example.week2.post.entity.Post;
import com.example.week2.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private int likeCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentStatus status;

    @Builder
    public Comment(String content, Post post, User user){
        this.content = content;
        this.post = post;
        this.user = user;
        this.likeCount = 0;
        this.status = CommentStatus.ACTIVE;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void hide(){
        this.status = CommentStatus.HIDDEN;
    }
}
