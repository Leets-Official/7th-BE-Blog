package com.example.leets_exercise1.repository;

import com.example.leets_exercise1.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    boolean existsByReporterIdAndPostId(Integer reporterId, Integer postId);

    boolean existsByReporterIdAndCommentId(Integer reporterId, Integer commentId);

    Optional<Report> findByIdAndActiveTrue(Integer reportId);
}