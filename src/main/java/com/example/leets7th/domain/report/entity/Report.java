package com.example.leets7th.domain.report.entity;

import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "reports",
        uniqueConstraints = @UniqueConstraint(columnNames = {"reporter_id", "target_type", "target_id"}))
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신고자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    // 신고 대상 타입 (POST / COMMENT)
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private ReportTargetType targetType;

    // 신고 대상 ID
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    // 신고 사유
    @Column(nullable = false)
    private String reason;

    // 신고 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;

    public static Report create(User reporter, ReportTargetType targetType, Long targetId, String reason) {
        Report report = new Report();
        report.reporter = reporter;
        report.targetType = targetType;
        report.targetId = targetId;
        report.reason = reason;
        report.status = ReportStatus.PENDING;
        return report;
    }

    public void resolve() {
        this.status = ReportStatus.RESOLVED;
    }
}
