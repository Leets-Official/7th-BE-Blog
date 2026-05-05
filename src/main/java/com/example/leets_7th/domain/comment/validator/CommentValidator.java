package com.example.leets_7th.domain.comment.validator;

import com.example.leets_7th.common.exception.GeneralException;
import com.example.leets_7th.common.status.ErrorStatus;
import com.example.leets_7th.domain.comment.entity.Comment;
import com.example.leets_7th.domain.comment.enums.CommentStatus;
import com.example.leets_7th.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentValidator {

    private final CommentRepository commentRepository;

    public Comment validateComment(Long postId, Long commentId) {

        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new GeneralException(ErrorStatus.DELETED_COMMENT);
        }

        if (comment.getStatus() == CommentStatus.HIDDEN) {
            throw new GeneralException(ErrorStatus.HIDDEN_COMMENT);
        }

        return comment;
    }

    // 관리자 전용
    public Comment validateCommentIncludingHidden(Long postId, Long commentId) {

        Comment comment = commentRepository.findByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));

        if (comment.getStatus() == CommentStatus.DELETED) {
            throw new GeneralException(ErrorStatus.DELETED_COMMENT);
        }

        return comment;
    }
}
