package com.example.leets7th.domain.report.service;

import com.example.leets7th.domain.comment.repository.CommentRepository;
import com.example.leets7th.domain.post.repository.PostRepository;
import com.example.leets7th.domain.report.dto.ReportRequest;
import com.example.leets7th.domain.report.entity.Report;
import com.example.leets7th.domain.report.entity.ReportStatus;
import com.example.leets7th.domain.report.entity.ReportTargetType;
import com.example.leets7th.domain.report.repository.ReportRepository;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.exception.AlreadyResolvedException;
import com.example.leets7th.global.exception.CommentNotFoundException;
import com.example.leets7th.global.exception.DuplicateReportException;
import com.example.leets7th.global.exception.PostNotFoundException;
import com.example.leets7th.global.exception.ReportNotFoundException;
import com.example.leets7th.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long reportPost(Long postId, ReportRequest request, Long reporterId) {
        // 게시글 존재 확인
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new UserNotFoundException(reporterId));

        // 중복 신고 방지
        if (reportRepository.existsByReporterIdAndTargetTypeAndTargetId(reporterId, ReportTargetType.POST, postId)) {
            throw new DuplicateReportException();
        }

        Report report = Report.create(reporter, ReportTargetType.POST, postId, request.reason());
        reportRepository.save(report);
        return report.getId();
    }

    @Transactional
    public Long reportComment(Long commentId, ReportRequest request, Long reporterId) {
        // 댓글 존재 확인
        commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new UserNotFoundException(reporterId));

        // 중복 신고 방지
        if (reportRepository.existsByReporterIdAndTargetTypeAndTargetId(reporterId, ReportTargetType.COMMENT, commentId)) {
            throw new DuplicateReportException();
        }

        Report report = Report.create(reporter, ReportTargetType.COMMENT, commentId, request.reason());
        reportRepository.save(report);
        return report.getId();
    }

    @Transactional
    public void resolveReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException(reportId));

        if (report.getStatus() == ReportStatus.RESOLVED) {
            throw new AlreadyResolvedException();
        }

        report.resolve();
    }
}
