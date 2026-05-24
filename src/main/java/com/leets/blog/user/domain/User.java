package com.leets.blog.user.domain;


import com.leets.blog.global.BaseTimeEntity;
import com.leets.blog.comment.domain.Comment;
import com.leets.blog.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 생성, 외부 접근 PROTECTED 제한
@Table(name = "users", indexes = {
    @Index(name = "idx_oauth_provider_id", columnList = "oauthProvider, oauthId")
})
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")   // DB 컬럼명 지정(ERD 식별자)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(length = 30)
    private String oauthProvider;

    @Column(length = 100)
    private String oauthId;

    // role (권한) 설정
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public User(String email, String password, String nickname, UserRole role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public void linkOAuth(String oauthProvider, String oauthId) {
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
    }
}
