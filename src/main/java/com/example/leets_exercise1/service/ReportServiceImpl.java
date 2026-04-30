package com.example.leets_exercise1.service;

import com.example.leets_exercise1.domain.comment.Comment;
import com.example.leets_exercise1.domain.post.Post;
import com.example.leets_exercise1.domain.report.Report;
import com.example.leets_exercise1.domain.report.ReportStatus;
import com.example.leets_exercise1.domain.user.User;
import com.example.leets_exercise1.dto.report.request.CommentReportCreateRequest;
import com.example.leets_exercise1.dto.report.request.PostReportCreateRequest;
import com.example.leets_exercise1.dto.report.response.ReportCreateResponse;
import com.example.leets_exercise1.dto.report.response.ReportResolveResponse;
import com.example.leets_exercise1.exception.CommentNotFoundException;
import com.example.leets_exercise1.exception.DuplicateReportException;
import com.example.leets_exercise1.exception.PostNotFoundException;
import com.example.leets_exercise1.exception.ReportNotFoundException;
import com.example.leets_exercise1.exception.UserNotFoundException;
import com.example.leets_exercise1.repository.CommentRepository;
import com.example.leets_exercise1.repository.PostRepository;
import com.example.leets_exercise1.repository.ReportRepository;
import com.example.leets_exercise1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReportCreateResponse reportPost(Long postId, PostReportCreateRequest request) {
        Post post = postRepository.findByIdAndDeletedAtIsNullAndActiveTrue(postId)
                .orElseThrow(PostNotFoundException::new);

        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(UserNotFoundException::new);

        if (reportRepository.existsByReporterIdAndPostId(reporter.getId(), post.getId())) {
            throw new DuplicateReportException();
        }

        Report report = Report.builder()
                .reporter(reporter)
                .post(post)
                .title(request.getTitle())
                .status(ReportStatus.PENDING)
                .active(true)
                .build();

        Report savedReport = reportRepository.save(report);

        return new ReportCreateResponse(
                savedReport.getId(),
                "POST",
                post.getId(),
                savedReport.getStatus(),
                savedReport.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public ReportCreateResponse reportComment(Long commentId, CommentReportCreateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(UserNotFoundException::new);

        if (reportRepository.existsByReporterIdAndCommentId(reporter.getId(), comment.getId())) {
            throw new DuplicateReportException();
        }

        Report report = Report.builder()
                .reporter(reporter)
                .comment(comment)
                .title(request.getTitle())
                .status(ReportStatus.PENDING)
                .active(true)
                .build();

        Report savedReport = reportRepository.save(report);

        return new ReportCreateResponse(
                savedReport.getId(),
                "COMMENT",
                comment.getId(),
                savedReport.getStatus(),
                savedReport.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public ReportResolveResponse resolveReport(Long reportId) {
        Report report = reportRepository.findByIdAndActiveTrue(reportId)
                .orElseThrow(ReportNotFoundException::new);

        report.resolve();

        return new ReportResolveResponse(
                report.getId(),
                report.getStatus()
        );
    }
}