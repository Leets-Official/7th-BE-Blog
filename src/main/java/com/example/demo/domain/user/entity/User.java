package com.example.demo.domain.user.entity;

import com.example.demo.domain.comment.entity.Comment;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    // 유저 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 닉네임
    @Column(nullable = false, unique = true, length = 10)
    private String name;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole role;

    @Column(length = 500)
    private String refreshToken;

    @Column(unique = true)
    private Long kakaoId;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    private User(String name) {
        this.name = name;
    }

    public static User of(String name) {
        return new User(name);
    }

    private User(String email, String nickname, String password, UserRole role) {
        this.email = email;
        this.name = nickname;
        this.password = password;
        this.role = role;
    }

    public static User register(String email, String nickname, String password) {
        return new User(email, nickname, password, UserRole.USER);
    }

    public static User registerKakao(Long kakaoId, String email, String nickname) {
        User user = new User(email, nickname, null, UserRole.USER);
        user.kakaoId = kakaoId;
        return user;
    }

    public String getNickname() {
        return name;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void clearRefreshToken() {
        this.refreshToken = null;
    }

    public void linkKakao(Long kakaoId) {
        this.kakaoId = kakaoId;
    }
}
