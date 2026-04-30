package com.example.springbootassignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springbootassignment.domain.report.entity.Report;
import com.example.springbootassignment.domain.report.entity.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> createReport(
            @RequestParam String reportType,
            @RequestParam Long targetId,
            @RequestParam Long reporterId,
            @RequestParam String reason) {

        Report report = reportService.createReport(reportType, targetId, reporterId, reason);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }
}
