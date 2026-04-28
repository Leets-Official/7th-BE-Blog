package com.example.demo.domain.report.repository;

import com.example.demo.domain.report.entity.Report;
import com.example.demo.domain.report.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByReporterIdAndPostIdAndStatus(Long reporterId, Long postId, ReportStatus status);

    boolean existsByReporterIdAndCommentIdAndStatus(Long reporterId, Long commentId, ReportStatus status);
}
