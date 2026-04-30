package com.example.week2.comment.service;

import com.example.week2.comment.dto.CommentReportRequest;
import com.example.week2.comment.dto.CommentResponse;
import com.example.week2.comment.entity.Comment;
import com.example.week2.comment.entity.CommentReport;
import com.example.week2.comment.repository.CommentReportRepository;
import com.example.week2.report.entity.ReportStatus;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import com.example.week2.global.response.CustomException;
import com.example.week2.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse.CommentReportResponse reportComment(
            Long commentId,
            CommentReportRequest request
    ) {

        Comment comment = commentService.getComment(commentId);

        User reporter = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new CustomException(ErrorCode.USER_NOT_FOUND)
                );


        if (commentReportRepository.existsByCommentAndReporter(comment, reporter)) {
            throw new CustomException(ErrorCode.REPORT_COMMENT_ALREADY_RESOLVED);
        }

        CommentReport report = CommentReport.builder()
                .comment(comment)
                .reporter(reporter)
                .reason(request.getReason())
                .build();

        CommentReport saved = commentReportRepository.save(report);

        return CommentResponse.CommentReportResponse.builder()
                .reportId(saved.getId())
                .commentId(comment.getId())
                .reason(saved.getReason())
                .status(saved.getStatus().name())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Transactional
    public CommentResponse.CommentReportResolve resolveReport(Long reportId) {

        CommentReport report = commentReportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (report.getStatus() == ReportStatus.RESOLVED) {
            throw new CustomException(ErrorCode.REPORT_COMMENT_ALREADY_RESOLVED);
        }

        report.resolve();

        return CommentResponse.CommentReportResolve.builder()
                .reportId(report.getId())
                .commentId(report.getComment().getId())
                .reason(report.getReason())
                .status(report.getStatus().name())
                .createdAt(report.getCreatedAt())
                .build();
    }
}