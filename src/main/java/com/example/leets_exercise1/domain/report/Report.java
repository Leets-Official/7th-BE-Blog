package com.example.leets_exercise1.domain.report;

import com.example.leets_exercise1.domain.comment.Comment;
import com.example.leets_exercise1.domain.post.Post;
import com.example.leets_exercise1.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 신고한 유저
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(nullable = false, length = 255)
    private String title;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportBlock> reportBlocks = new ArrayList<>();

    @Builder
    public Report(User reporter, Post post, Comment comment, String title, Boolean active) {
        this.reporter = reporter;
        this.post = post;
        this.comment = comment;
        this.title = title;
        this.active = active != null ? active : true;
    }

    @PrePersist
    @PreUpdate
    private void validateTarget() {
        boolean hasPost = this.post != null;
        boolean hasComment = this.comment != null;

        if (hasPost == hasComment) {
            throw new IllegalStateException("신고 대상은 Post 또는 Comment 중 하나만 가져야 합니다.");
        }
    }
}