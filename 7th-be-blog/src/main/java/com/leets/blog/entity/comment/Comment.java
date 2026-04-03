package com.leets.blog.entity.comment;

import com.leets.blog.entity.BaseEntity;
import com.leets.blog.entity.post.Post;
import com.leets.blog.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment extends BaseEntity {

    // - PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // - 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // - FK - user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // - FK - post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}