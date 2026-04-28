package com.example.leets_7th.domain.comment.service;

import com.example.leets_7th.common.exception.GeneralException;
import com.example.leets_7th.common.status.ErrorStatus;
import com.example.leets_7th.domain.comment.dto.request.CreateCommentRequest;
import com.example.leets_7th.domain.comment.dto.request.ReportCommentRequest;
import com.example.leets_7th.domain.comment.dto.request.UpdateCommentRequest;
import com.example.leets_7th.domain.comment.entity.Comment;
import com.example.leets_7th.domain.comment.entity.CommentLike;
import com.example.leets_7th.domain.comment.entity.CommentReport;
import com.example.leets_7th.domain.comment.repository.CommentLikeRepository;
import com.example.leets_7th.domain.comment.repository.CommentReportRepository;
import com.example.leets_7th.domain.comment.repository.CommentRepository;
import com.example.leets_7th.domain.comment.validator.CommentValidator;
import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.validator.PostValidator;
import com.example.leets_7th.domain.user.entity.User;
import com.example.leets_7th.domain.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {

    private final PostValidator postValidator;
    private final UserValidator userValidator;
    private final CommentValidator commentValidator;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentReportRepository commentReportRepository;

    public void createComment(Long userId, Long postId, CreateCommentRequest request) {

        User user = userValidator.validateUser(userId);
        Post post = postValidator.validatePost(postId);

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.comment())
                .build();

        commentRepository.save(comment);
    }

    public void deleteComment(Long userId, Long postId, Long commentId) {
        User user = userValidator.validateUser(userId);
        Comment comment = commentValidator.validateComment(postId, commentId);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new GeneralException(ErrorStatus.FORBIDDEN_COMMENT_DELETE);
        }

        comment.delete();
    }

    public void updateComment(Long userId, Long postId, Long commentId, UpdateCommentRequest request) {

        User user = userValidator.validateUser(userId);
        postValidator.validatePost(postId);

        Comment comment = commentValidator.validateComment(postId,commentId);
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new GeneralException(ErrorStatus.FORBIDDEN_COMMENT_UPDATE);
        }
        comment.update(request.comment());
    }

    public void likeComment(Long userId, Long postId, Long commentId) {
        User user = userValidator.validateUser(userId);
        Comment comment = commentValidator.validateComment(postId, commentId);

        if (commentLikeRepository.existsByCommentIdAndUserId(commentId, userId)) {
            throw new GeneralException(ErrorStatus.ALREADY_LIKED_COMMENT);
        }

        commentLikeRepository.save(CommentLike.of(comment, user));
        comment.increaseLikeCount();
    }

    public void reportComment(Long userId, Long postId, Long commentId, ReportCommentRequest request) {
        User user = userValidator.validateUser(userId);
        Comment comment = commentValidator.validateComment(postId, commentId);

        if (commentReportRepository.existsByCommentIdAndUserId(commentId, userId)) {
            throw new GeneralException(ErrorStatus.ALREADY_REPORTED_COMMENT);
        }

        CommentReport commentReport = CommentReport.of(
                comment,
                user,
                request.reason(),
                request.content()
        );

        commentReportRepository.save(commentReport);

        long reportCount = commentReportRepository.countByCommentId(commentId);

        if (reportCount >= 10) {
            comment.hideByReports();
        }
    }

    public void unlikeComment(Long userId, Long postId, Long commentId) {
        userValidator.validateUser(userId);
        Comment comment = commentValidator.validateComment(postId, commentId);

        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_LIKE_NOT_FOUND));

        commentLikeRepository.delete(commentLike);
        comment.decreaseLikeCount();
    }

}
