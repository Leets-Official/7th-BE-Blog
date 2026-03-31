package com.leets.blog.entity.user;

import com.leets.blog.entity.BaseEntity;
import com.leets.blog.entity.post.Post;
import com.leets.blog.entity.comment.Comment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User extends BaseEntity {

    // - pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // - 유저 이름
    @Column(nullable = false)
    private String name;

    // - 이메일
    @Column(nullable = false, unique = true)
    private String email;

    // - 패스워드
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    // user 데이터를 지우지 않고 상태변경 -> FK 보존
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}