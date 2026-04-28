package com.example.leets7th.domain.report.repository;

import com.example.leets7th.domain.report.entity.Report;
import com.example.leets7th.domain.report.entity.ReportTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByReporterIdAndTargetTypeAndTargetId(Long reporterId, ReportTargetType targetType, Long targetId);
}
