package com.example.leets_exercise1.repository;

import com.example.leets_exercise1.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByReporterIdAndPostId(Long reporterId, Long postId);

    boolean existsByReporterIdAndCommentId(Long reporterId, Long commentId);

    Optional<Report> findByIdAndActiveTrue(Long reportId);
}