package com.leets.blog.domain.post.entity;

import com.leets.blog.global.common.BaseEntity;
import com.leets.blog.domain.comment.entity.Comment;
import com.leets.blog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Post extends BaseEntity {

    // - pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // - 제목
    @Column(nullable = false)
    private String title;

    // - 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // - FK - 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Post - Comment (1:N)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 상태 변경
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // 게시글 수정 메서드
    public void update(String title, String content) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (content != null && !content.isBlank()) {
            this.content = content;
        }
    }
}