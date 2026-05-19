package com.example.leets_7th.domain.user.entity;

import com.example.leets_7th.common.base.BaseEntity;
import com.example.leets_7th.domain.comment.entity.Comment;
import com.example.leets_7th.domain.user.enums.Gender;
import com.example.leets_7th.domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "`user`")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String kakaoId;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public static User create(String name, Gender gender, String email, String password, int age) {
        User user = new User();
        user.name = name;
        user.gender = gender;
        user.email = email;
        user.password = password;
        user.age = age;
        user.role = Role.USER;
        return user;
    }

    public static User createByKakao(String name, String email, String kakaoId) {
        User user = new User();
        user.name = name;
        user.email = email;
        user.kakaoId = kakaoId;
        user.password = "";
        user.role = Role.USER;
        return user;
    }
}
