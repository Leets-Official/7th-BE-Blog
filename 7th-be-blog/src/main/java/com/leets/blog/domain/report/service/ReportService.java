package com.leets.blog.domain.report.service;

import com.leets.blog.common.exception.BaseErrorCode;
import com.leets.blog.common.exception.GeneralException;
import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.post.repository.PostRepository;
import com.leets.blog.domain.report.dto.CreatePostReportRequest;
import com.leets.blog.domain.report.dto.ReportResponse;
import com.leets.blog.domain.report.entity.Report;
import com.leets.blog.domain.report.repository.ReportRepository;
import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public ReportResponse createPostReport(Long postId, CreatePostReportRequest request) {
        User user = getUser(request.userId());
        Post post = getPost(postId);

        if (reportRepository.existsByUserAndPost(user, post)) {
            throw new GeneralException(BaseErrorCode.DUPLICATE_REPORT);
        }

        Report report = Report.create(user, post, request.reason());
        Report savedReport = reportRepository.save(report);

        return ReportResponse.builder()
                .reportId(savedReport.getId())
                .postId(savedReport.getPost().getId())
                .reporterId(savedReport.getUser().getId())
                .reason(savedReport.getReason())
                .status(savedReport.getStatus())
                .createdAt(savedReport.getCreatedAt())
                .build();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new GeneralException(BaseErrorCode.USER_NOT_FOUND));
    }

    private Post getPost(Long postId) {
        return postRepository.findByIdAndIsDeletedFalse(postId)
                .orElseThrow(() -> new GeneralException(BaseErrorCode.POST_NOT_FOUND));
    }
}
