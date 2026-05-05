package com.example.leets_7th.domain.post.entity;

import com.example.leets_7th.common.base.BaseEntity;
import com.example.leets_7th.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Table(
        name = "post_like",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_post_like_user_post",
                        columnNames = {"user_id", "post_id"}
                )
        }
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public static PostLike of(User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .build();
    }
}
