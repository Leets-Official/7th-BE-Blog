package com.leets.blog.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {

    public Comment() {}

    public Comment(String content) {
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean liked;

    @Column(nullable = false)
    private LocalDateTime createAt;

    // Comment는 하나의 User와 연결됨 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK: userId
    private User user;

    // Comment는 하나의 Post에 속함 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id") // FK: postId
    private Post post;


}
