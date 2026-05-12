package com.leets.blog.controller;

import com.leets.blog.dto.ReportRequest;
import com.leets.blog.entity.Report;
import com.leets.blog.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Void> report(@RequestBody ReportRequest request) {
        reportService.report(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAll() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @PatchMapping("/{reportId}/process")
    public ResponseEntity<Void> process(@PathVariable Long reportId, @RequestParam boolean approve) {
        reportService.processReport(reportId, approve);
        return ResponseEntity.ok().build();
    }
}