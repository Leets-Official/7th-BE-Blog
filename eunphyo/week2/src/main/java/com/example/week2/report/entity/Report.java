package com.example.week2.report.entity;

import com.example.week2.global.entity.BaseEntity;
import com.example.week2.post.entity.Post;
import com.example.week2.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Report(String reason, Post post, User user) {
        this.reason = reason;
        this.post = post;
        this.user = user;
        this.status = ReportStatus.PENDING;
    }
}
