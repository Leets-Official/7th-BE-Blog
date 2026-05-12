package com.leets.blog.repository;

import com.leets.blog.entity.Report;
import com.leets.blog.entity.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByTargetIdAndTargetTypeAndReporterId(Long targetId, TargetType targetType, Long reporterId);
}