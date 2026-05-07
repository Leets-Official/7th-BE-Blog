package com.example.demo.domain.report.service;

import com.example.demo.domain.comment.entity.Comment;
import com.example.demo.domain.comment.entity.CommentStatus;
import com.example.demo.domain.comment.service.CommentService;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.entity.PostStatus;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.report.dto.ReportCreateRequest;
import com.example.demo.domain.report.dto.ReportCreateResponse;
import com.example.demo.domain.report.dto.ReportResolveRequest;
import com.example.demo.domain.report.dto.ReportResolveResponse;
import com.example.demo.domain.report.entity.Report;
import com.example.demo.domain.report.entity.ReportResolutionType;
import com.example.demo.domain.report.entity.ReportStatus;
import com.example.demo.domain.report.repository.ReportRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    @Transactional
    public ReportCreateResponse reportPost(Long postId, ReportCreateRequest request) {
        Post post = findPost(postId);
        User reporter = findUser(request.reporterId());

        if (post.getUser().getId().equals(reporter.getId())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "SELF_REPORT_NOT_ALLOWED", "본인 게시글은 신고할 수 없습니다.");
        }
        if (reportRepository.existsByReporterIdAndPostIdAndStatus(reporter.getId(), postId, ReportStatus.PENDING)) {
            throw new CustomException(HttpStatus.CONFLICT, "DUPLICATE_REPORT", "이미 처리 대기 중인 게시글 신고가 있습니다.");
        }

        Report report = reportRepository.save(Report.forPost(reporter, post, request.reason()));
        return new ReportCreateResponse(report.getId(), report.getTargetType(), report.getStatus());
    }

    @Transactional
    public ReportCreateResponse reportComment(Long commentId, ReportCreateRequest request) {
        Comment comment = commentService.findComment(commentId);
        User reporter = findUser(request.reporterId());

        if (comment.getUser().getId().equals(reporter.getId())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "SELF_REPORT_NOT_ALLOWED", "본인 댓글은 신고할 수 없습니다.");
        }
        if (reportRepository.existsByReporterIdAndCommentIdAndStatus(reporter.getId(), commentId, ReportStatus.PENDING)) {
            throw new CustomException(HttpStatus.CONFLICT, "DUPLICATE_REPORT", "이미 처리 대기 중인 댓글 신고가 있습니다.");
        }

        Report report = reportRepository.save(Report.forComment(reporter, comment, request.reason()));
        return new ReportCreateResponse(report.getId(), report.getTargetType(), report.getStatus());
    }

    @Transactional
    public ReportResolveResponse resolveReport(Long reportId, ReportResolveRequest request) {
        Report report = findReport(reportId);
        User resolver = findUser(request.resolverId());

        if (report.getStatus() == ReportStatus.RESOLVED) {
            throw new CustomException(HttpStatus.CONFLICT, "REPORT_ALREADY_RESOLVED", "이미 처리 완료된 신고입니다.");
        }

        applyResolution(report, request.resolutionType());
        report.resolve(resolver, request.resolutionType());

        return new ReportResolveResponse(report.getId(), report.getStatus(), report.getResolutionType());
    }

    private void applyResolution(Report report, ReportResolutionType resolutionType) {
        switch (resolutionType) {
            case HIDE_POST -> {
                if (report.getPost() == null) {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_RESOLUTION", "댓글 신고에는 게시글 숨김 처리를 할 수 없습니다.");
                }
                if (report.getPost().getStatus() == PostStatus.HIDDEN) {
                    throw new CustomException(HttpStatus.CONFLICT, "POST_ALREADY_HIDDEN", "이미 숨김 처리된 게시글입니다.");
                }
                report.getPost().hide();
            }
            case HIDE_COMMENT -> {
                if (report.getComment() == null) {
                    throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_RESOLUTION", "게시글 신고에는 댓글 숨김 처리를 할 수 없습니다.");
                }
                if (report.getComment().getStatus() == CommentStatus.HIDDEN) {
                    throw new CustomException(HttpStatus.CONFLICT, "COMMENT_ALREADY_HIDDEN", "이미 숨김 처리된 댓글입니다.");
                }
                report.getComment().hide();
            }
            case REJECT -> {
                // Intentionally left blank. Reject only changes the report state.
            }
        }
    }

    private Report findReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "REPORT_NOT_FOUND", "해당 신고를 찾을 수 없습니다."));
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글을 찾을 수 없습니다."));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."));
    }
}
