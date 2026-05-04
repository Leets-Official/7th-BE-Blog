package com.example.week2.report.service;

import com.example.week2.comment.service.CommentService;
import com.example.week2.report.dto.ReportRequest;
import com.example.week2.comment.entity.Comment;
import com.example.week2.report.dto.ReportResponse;
import com.example.week2.report.entity.Report;
import com.example.week2.report.entity.ReportStatus;
import com.example.week2.report.repository.ReportRepository;
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
public class ReportService {

    private final ReportRepository commentReportRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @Transactional
    public ReportResponse.CommentReportResponse reportComment(
            Long commentId,
            ReportRequest request
    ) {

        Comment comment = commentService.getComment(commentId);

        User reporter = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new CustomException(ErrorCode.USER_NOT_FOUND)
                );


        if (commentReportRepository.existsByCommentAndReporter(comment, reporter)) {
            throw new CustomException(ErrorCode.REPORT_COMMENT_ALREADY_RESOLVED);
        }

        Report report = Report.builder()
                .post(comment.getPost())
                .comment(comment)
                .reporter(reporter)
                .reason(request.getReason())
                .build();

        Report saved = commentReportRepository.save(report);

        return ReportResponse.CommentReportResponse.builder()
                .reportId(saved.getId())
                .commentId(comment.getId())
                .reason(saved.getReason())
                .status(saved.getStatus().name())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Transactional
    public ReportResponse.CommentReportResolve resolveReport(Long reportId) {

        Report report = commentReportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));

        if (report.getStatus() == ReportStatus.RESOLVED) {
            throw new CustomException(ErrorCode.REPORT_COMMENT_ALREADY_RESOLVED);
        }

        report.resolve();
        Comment comment = report.getComment();
        comment.hide();

        return ReportResponse.CommentReportResolve.builder()
                .reportId(report.getId())
                .commentId(report.getComment().getId())
                .reason(report.getReason())
                .status(report.getStatus().name())
                .createdAt(report.getCreatedAt())
                .build();
    }


}