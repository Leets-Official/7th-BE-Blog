package com.example.week2.report.entity;

import com.example.week2.comment.entity.Comment;
import com.example.week2.global.entity.BaseEntity;
import com.example.week2.post.entity.Post;
import com.example.week2.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment_reports")
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @Builder
    public Report(String reason, Post post, Comment comment, User reporter) {
        this.reason = reason;
        this.post = post;
        this.comment = comment;
        this.reporter = reporter;
        this.status = ReportStatus.PENDING;
    }

    public void resolve() {
        this.status = ReportStatus.RESOLVED;
    }

}
