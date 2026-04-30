package com.example.week2.comment.service;

import com.example.week2.comment.dto.CommentResponse;
import com.example.week2.comment.entity.Comment;
import com.example.week2.comment.entity.CommentLike;
import com.example.week2.comment.repository.CommentLikeRepository;
import com.example.week2.global.response.CustomException;
import com.example.week2.global.response.ErrorCode;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse.CommentLikeResponse likeComment(Long commentId, Long userId) {

        Comment comment = commentService.getComment(commentId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (commentLikeRepository.existsByCommentAndUser(comment, user)) {
            throw new CustomException(ErrorCode.COMMENT_ALREADY_LIKED);
        }

        CommentLike like = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();

        commentLikeRepository.save(like);

        comment.increaseLikeCount();

        return CommentResponse.CommentLikeResponse.builder()
                .commentId(comment.getId())
                .userId(user.getId())
                .likeCount(comment.getLikeCount())
                .build();
    }
}