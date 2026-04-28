package com.example.demo.domain.report.entity;

import com.example.demo.domain.comment.entity.Comment;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.user.entity.User;
import com.example.demo.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolver_id")
    private User resolver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReportTargetType targetType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReportStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ReportResolutionType resolutionType;

    @Column(nullable = false, length = 255)
    private String reason;

    private Report(User reporter, Post post, Comment comment, ReportTargetType targetType, String reason) {
        this.reporter = reporter;
        this.post = post;
        this.comment = comment;
        this.targetType = targetType;
        this.reason = reason;
        this.status = ReportStatus.PENDING;
    }

    public static Report forPost(User reporter, Post post, String reason) {
        return new Report(reporter, post, null, ReportTargetType.POST, reason);
    }

    public static Report forComment(User reporter, Comment comment, String reason) {
        return new Report(reporter, null, comment, ReportTargetType.COMMENT, reason);
    }

    public void resolve(User resolver, ReportResolutionType resolutionType) {
        this.resolver = resolver;
        this.resolutionType = resolutionType;
        this.status = ReportStatus.RESOLVED;
    }
}
