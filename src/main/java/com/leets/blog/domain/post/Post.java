package com.leets.blog.domain.post;

import com.leets.blog.domain.BaseTimeEntity;
import com.leets.blog.domain.user.User;
import com.leets.blog.domain.comment.Comment;
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
    private PostStatus status;          // PUBLISHED, DRAFT

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



}
