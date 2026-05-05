package com.example.demo.report.service;

import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.global.exception.BaseCode;
import com.example.demo.global.exception.CustomException;
import com.example.demo.post.entity.Post;
import com.example.demo.post.repository.PostRepository;
import com.example.demo.report.dto.ReportCreateRequest;
import com.example.demo.report.dto.ReportResponse;
import com.example.demo.report.entity.Report;
import com.example.demo.report.entity.ReportStatus;
import com.example.demo.report.entity.ReportTargetType;
import com.example.demo.report.repository.ReportRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 게시글 신고
    public ReportResponse reportPost(Long postId, ReportCreateRequest request) {
        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new CustomException(BaseCode.USER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(BaseCode.POST_NOT_FOUND));

        boolean alreadyReported = reportRepository.existsByReporterAndPostAndStatus(
                reporter,
                post,
                ReportStatus.PENDING
        );

        if (alreadyReported) {
            throw new CustomException(BaseCode.ALREADY_REPORTED_POST);
        }

        Report report = Report.builder()
                .reporter(reporter)
                .post(post)
                .targetType(ReportTargetType.POST)
                .reason(request.getReason())
                .status(ReportStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Report savedReport = reportRepository.save(report);

        return ReportResponse.from(savedReport);
    }

    // 댓글 신고
    public ReportResponse reportComment(Long commentId, ReportCreateRequest request) {
        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new CustomException(BaseCode.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(BaseCode.COMMENT_NOT_FOUND));

        boolean alreadyReported = reportRepository.existsByReporterAndCommentAndStatus(
                reporter,
                comment,
                ReportStatus.PENDING
        );

        if (alreadyReported) {
            throw new CustomException(BaseCode.ALREADY_REPORTED_COMMENT);
        }

        Report report = Report.builder()
                .reporter(reporter)
                .comment(comment)
                .targetType(ReportTargetType.COMMENT)
                .reason(request.getReason())
                .status(ReportStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Report savedReport = reportRepository.save(report);

        return ReportResponse.from(savedReport);
    }

    // 신고 처리 완료
    public ReportResponse resolveReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(BaseCode.REPORT_NOT_FOUND));

        if (report.getStatus() == ReportStatus.RESOLVED) {
            throw new CustomException(BaseCode.ALREADY_RESOLVED_REPORT);
        }

        report.resolve();

        if (report.getTargetType() == ReportTargetType.POST) {
            report.getPost().hide();
        }

        if (report.getTargetType() == ReportTargetType.COMMENT) {
            report.getComment().hide();
        }

        return ReportResponse.from(report);
    }
}