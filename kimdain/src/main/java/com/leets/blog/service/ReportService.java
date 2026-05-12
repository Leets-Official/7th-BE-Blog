package com.leets.blog.service;

import com.leets.blog.dto.ReportRequest;
import com.leets.blog.entity.*;
import com.leets.blog.repository.*;
import com.leets.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leets.blog.entity.enums.ContentStatus;
import com.leets.blog.entity.enums.TargetType;
import com.leets.blog.entity.enums.ReportStatus;
import com.leets.blog.entity.Report;
import com.leets.blog.entity.Reportable;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private static final int THRESHOLD = 5;

    public void report(ReportRequest request) {
        if (reportRepository.existsByTargetIdAndTargetTypeAndReporterId(
                request.targetId(), request.targetType(), request.reporterId())) {
            throw new RuntimeException("이미 신고한 항목입니다.");
        }

        Reportable target = findTarget(request.targetId(), request.targetType());
        target.addWeight(request.reason().getWeight());

        if (target.getTotalWeight() >= THRESHOLD) {
            target.updateStatus(ContentStatus.HIDDEN);
        }
        reportRepository.save(request.toEntity());
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public void processReport(Long reportId, boolean approve) {
        Report report = reportRepository.findById(reportId).orElseThrow();
        report.setStatus(ReportStatus.RESOLVED);

        if (!approve) { // 반려(REJECT) 시 활성 상태로 복구
            Reportable target = findTarget(report.getTargetId(), report.getTargetType());
            target.updateStatus(ContentStatus.ACTIVE);
        }
    }

    private Reportable findTarget(Long id, TargetType type) {
        if (type == TargetType.POST) return postRepository.findById(id).orElseThrow();
        return commentRepository.findById(id).orElseThrow();
    }
}