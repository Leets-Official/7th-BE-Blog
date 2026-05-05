package com.example.leets_7th.domain.post.entity;

import com.example.leets_7th.common.base.BaseEntity;
import com.example.leets_7th.domain.comment.enums.ReportReason;
import com.example.leets_7th.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Table(
        name = "post_report",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_post_report_user_post",
                        columnNames = {"user_id", "post_id"}
                )
        }
)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PostReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    @Column(columnDefinition = "TEXT")
    private String content;

    public static PostReport of(User user, Post post, ReportReason reason, String content) {
        return PostReport.builder()
                .user(user)
                .post(post)
                .reason(reason)
                .content(content)
                .build();
    }
}
