package com.example.leets7th.domain.user.entity;

import com.example.leets7th.domain.comment.entity.Comment;
import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 30)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Long kakaoId;

    private String refreshToken;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public static User create(String email, String encodedPassword, String nickname) {
        User user = new User();
        user.email = email;
        user.password = encodedPassword;
        user.nickname = nickname;
        user.role = UserRole.USER;
        return user;
    }

    public static User createByKakao(Long kakaoId, String email, String nickname, String encodedTempPassword) {
        User user = new User();
        user.kakaoId = kakaoId;
        user.email = email;
        user.password = encodedTempPassword;
        user.nickname = nickname;
        user.role = UserRole.USER;
        return user;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
