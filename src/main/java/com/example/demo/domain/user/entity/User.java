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
public class User extends BaseEntity {

    // 유저 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    @Column(nullable = false, length = 10)
    private String name;

    // 성별
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    // 나이
    @Column
    private Integer age;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();
}