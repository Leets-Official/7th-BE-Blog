package com.example.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 이름
    private String name;

    // 이메일 로그인용 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // 사용자 닉네임
    @Column(nullable = false, unique = true)
    private String nickname;

    // 이메일 로그인용 비밀번호
    @Column(nullable = false)
    private String password;

    // 카카오 로그인 사용자 식별 ID
    @Column(unique = true)
    private Long kakaoId;

    // Refresh Token 저장
    private String refreshToken;

    @Builder
    public User(
            String name,
            String email,
            String nickname,
            String password,
            Long kakaoId
    ) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.kakaoId = kakaoId;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
