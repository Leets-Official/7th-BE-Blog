package com.leets.blog.domain.report.service;

import com.leets.blog.common.exception.BaseErrorCode;
import com.leets.blog.common.exception.GeneralException;
import com.leets.blog.domain.comment.entity.Comment;
import com.leets.blog.domain.comment.repository.CommentRepository;
import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.post.repository.PostRepository;
import com.leets.blog.domain.report.dto.CommentReportResponse;
import com.leets.blog.domain.report.dto.CreateCommentReportRequest;
import com.leets.blog.domain.report.dto.CreatePostReportRequest;
import com.leets.blog.domain.report.dto.PostReportResponse;
import com.leets.blog.domain.report.entity.CommentReport;
import com.leets.blog.domain.report.entity.PostReport;
import com.leets.blog.domain.report.repository.CommentReportRepository;
import com.leets.blog.domain.report.repository.PostReportRepository;
import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final PostReportRepository postReportRepository;
    private final CommentReportRepository commentReportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostReportResponse createPostReport(Long postId, CreatePostReportRequest request) {
        User user = getUser(request.userId());
        Post post = getPost(postId);

        if (postReportRepository.existsByUserAndPost(user, post)) {
            throw new GeneralException(BaseErrorCode.DUPLICATE_REPORT);
        }

        PostReport report = PostReport.create(user, post, request.reason());
        PostReport savedReport = postReportRepository.save(report);

        return PostReportResponse.builder()
                .reportId(savedReport.getId())
                .postId(savedReport.getPost().getId())
                .reporterId(savedReport.getUser().getId())
                .reason(savedReport.getReason())
                .status(savedReport.getStatus())
                .createdAt(savedReport.getCreatedAt())
                .build();
    }

    @Transactional
    public CommentReportResponse createCommentReport(Long commentId, CreateCommentReportRequest request) {
        User user = getUser(request.userId());
        Comment comment = getComment(commentId);

        if (commentReportRepository.existsByUserAndComment(user, comment)) {
            throw new GeneralException(BaseErrorCode.DUPLICATE_REPORT);
        }

        CommentReport report = CommentReport.create(user, comment, request.reason());
        CommentReport savedReport = commentReportRepository.save(report);

        return CommentReportResponse.builder()
                .reportId(savedReport.getId())
                .commentId(savedReport.getComment().getId())
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

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new GeneralException(BaseErrorCode.COMMENT_NOT_FOUND));
    }
}
