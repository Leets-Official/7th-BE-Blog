package com.leets.blog.report.domain;

import com.leets.blog.global.BaseTimeEntity;
import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "reports",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_report_reporter_target",
                        columnNames = {"reporter_id", "target_type", "target_id"}
                )
        }
)
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ReportTargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(nullable = false, length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resolver_id")
    private User resolver;

    private LocalDateTime resolvedAt;

    public Report(ReportTargetType targetType, Long targetId, String reason, User reporter) {
        this.targetType = targetType;
        this.targetId = targetId;
        this.reason = reason;
        this.reporter = reporter;
        this.status = ReportStatus.PENDING;
    }

    public void resolve(User resolver) {
        if (this.status == ReportStatus.RESOLVED) {
            throw new BusinessException(ErrorCode.INVALID_STATE_TRANSITION);
        }
        this.status = ReportStatus.RESOLVED;
        this.resolver = resolver;
        this.resolvedAt = LocalDateTime.now();
    }
}
