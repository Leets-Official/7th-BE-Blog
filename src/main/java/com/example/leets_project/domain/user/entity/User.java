package com.example.leets_project.domain.user.entity;

import com.example.leets_project.common.entity.BaseEntity;
import com.example.leets_project.domain.comment.entity.Comment;
import com.example.leets_project.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50) // 동명이인 가능 -> unique 값 제외
    private String name;

    @Column(unique = true, length = 100)// Kakao test 위해 잠시 nullable 해제
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column // Kakao test 위해 잠시 nullable 해제
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AuthProvider authProvider;

    @Column(length = 100)
    private String providerId;

    // 양방향: User → Post
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    // 양방향: User → Comment
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public User(String name, String email, String nickname, String password, AuthProvider authProvider,  String providerId) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = UserRole.USER;
        this.authProvider = authProvider;
        this.providerId = providerId;
    }
}

