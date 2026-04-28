package com.example.springbootassignment.domain.report.entity;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report createReport(String reportType, Long targetId, Long reporterId, String reason) {
        Report report = new Report();
        report.setReportType(reportType);
        report.setTargetId(targetId);
        report.setReporterId(reporterId);
        report.setReason(reason);
        report.setStatus("PENDING");

        return reportRepository.save(report);
    }
}
