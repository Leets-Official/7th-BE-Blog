package com.example.week2.user.entity;

import com.example.week2.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_provider_provider_id",
                        columnNames = {"provider", "provider_id"}
                )
        }
)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private AuthProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    public enum Role {
        USER, ADMIN
    }

    public enum AuthProvider {
        LOCAL, KAKAO
    }

    @Builder
    public User(
            String name,
            String nickname,
            String email,
            String password,
            Role role,
            AuthProvider provider,
            String providerId
    ) {

        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role == null ? Role.USER : role;
        this.provider = provider == null ? AuthProvider.LOCAL : provider;
        this.providerId = providerId;
    }
}
