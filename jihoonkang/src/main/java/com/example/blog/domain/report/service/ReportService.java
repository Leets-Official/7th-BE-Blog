package com.example.blog.domain.report.service;

import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.comment.repository.CommentRepository;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.report.dto.ReportCreateRequest;
import com.example.blog.domain.report.dto.ReportResponse;
import com.example.blog.domain.report.entity.Report;
import com.example.blog.domain.report.repository.ReportRepository;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import com.example.blog.global.exception.BusinessException;
import com.example.blog.global.exception.ErrorCode;
import com.example.blog.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public ReportResponse create(Long reporterId, ReportCreateRequest request) {
        User reporter = userRepository.findById(reporterId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Post post = null;
        Comment comment = null;

        if ("POST".equalsIgnoreCase(request.targetType())) {
            post = postRepository.findById(request.targetId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));
        } else if ("COMMENT".equalsIgnoreCase(request.targetType())) {
            comment = commentRepository.findById(request.targetId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        } else {
            throw new BusinessException(ErrorCode.INVALID_REPORT_TARGET);
        }

        Report report = Report.of(reporter, post, comment, request.reason());
        return ReportResponse.from(reportRepository.save(report));
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> findAll() {
        return reportRepository.findAll().stream()
            .map(ReportResponse::from)
            .toList();
    }
}
