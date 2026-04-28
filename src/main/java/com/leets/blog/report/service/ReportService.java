package com.leets.blog.report.service;

import com.leets.blog.comment.repository.CommentRepository;
import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.post.repository.PostRepository;
import com.leets.blog.report.domain.Report;
import com.leets.blog.report.domain.ReportTargetType;
import com.leets.blog.report.dto.ReportRequest;
import com.leets.blog.report.dto.ReportResponse;
import com.leets.blog.report.repository.ReportRepository;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import com.leets.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public ReportResponse reportComment(AuthUser authUser, Long commentId, ReportRequest.Create request) {
        User reporter = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!commentRepository.existsById(commentId)) {
            throw new BusinessException(ErrorCode.COMMENT_NOT_FOUND);
        }

        if (reportRepository.existsByReporterIdAndTargetTypeAndTargetId(
                reporter.getId(),
                ReportTargetType.COMMENT,
                commentId
        )) {
            throw new BusinessException(ErrorCode.DUPLICATE_REPORT);
        }

        Report report = new Report(ReportTargetType.COMMENT, commentId, request.getReason(), reporter);
        Report savedReport = reportRepository.save(report);
        return new ReportResponse(savedReport);
    }

    @Transactional
    public ReportResponse reportPost(AuthUser authUser, Long postId, ReportRequest.Create request) {
        User reporter = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!postRepository.existsById(postId)) {
            throw new BusinessException(ErrorCode.POST_NOT_FOUND);
        }

        if (reportRepository.existsByReporterIdAndTargetTypeAndTargetId(
                reporter.getId(),
                ReportTargetType.POST,
                postId
        )) {
            throw new BusinessException(ErrorCode.DUPLICATE_REPORT);
        }

        Report report = new Report(ReportTargetType.POST, postId, request.getReason(), reporter);
        Report savedReport = reportRepository.save(report);
        return new ReportResponse(savedReport);
    }

    @Transactional
    public ReportResponse resolve(AuthUser authUser, Long reportId) {
        if (authUser.getRole() != UserRole.ADMIN) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        User resolver = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REPORT_NOT_FOUND));

        report.resolve(resolver);
        return new ReportResponse(report);
    }

    public List<ReportResponse> findAll(AuthUser authUser) {
        validateAdmin(authUser);
        return reportRepository.findAll().stream()
                .map(ReportResponse::new)
                .toList();
    }

    public ReportResponse findById(AuthUser authUser, Long reportId) {
        validateAdmin(authUser);
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REPORT_NOT_FOUND));
        return new ReportResponse(report);
    }

    private void validateAdmin(AuthUser authUser) {
        if (authUser.getRole() != UserRole.ADMIN) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
