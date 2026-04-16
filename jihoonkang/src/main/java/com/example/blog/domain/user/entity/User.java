package com.example.blog.domain.user.entity;

import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "profile_url", length = 200)
    private String profileUrl;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public static User of(String username, String email, String password, String profileUrl) {
        User user = new User();
        user.username = username;
        user.email = email;
        user.password = password;
        user.profileUrl = profileUrl;
        return user;
    }

    public void update(String username, String profileUrl) {
        if (username != null) {
            this.username = username;
        }
        if (profileUrl != null) {
            this.profileUrl = profileUrl;
        }
    }
}
