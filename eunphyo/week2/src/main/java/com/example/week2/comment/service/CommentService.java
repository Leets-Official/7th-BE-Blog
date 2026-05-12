package com.example.week2.comment.service;

import com.example.week2.comment.dto.CommentCreateRequest;
import com.example.week2.comment.dto.CommentResponse;
import com.example.week2.comment.entity.Comment;
import com.example.week2.comment.entity.CommentLike;
import com.example.week2.comment.entity.CommentStatus;
import com.example.week2.comment.repository.CommentLikeRepository;
import com.example.week2.comment.repository.CommentRepository;
import com.example.week2.post.entity.Post;
import com.example.week2.post.repository.PostRepository;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.week2.global.response.CustomException;
import com.example.week2.global.response.ErrorCode;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.util.ClassUtils.ifPresent;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponse.CreateCommentResponse createComment(
            Long postId,
            Long userId,
            CommentCreateRequest request
    ) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.CreateCommentResponse.builder()
                .commentId(savedComment.getId())
                .content(savedComment.getContent())
                .nickname(savedComment.getUser().getName())
                .createdAt(savedComment.getCreatedAt())
                .updatedAt(savedComment.getUpdatedAt())
                .build();
    }

    @Transactional
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public CommentResponse.CommentDetailResponse getCommentResponse(Long commentId) {

        Comment comment = commentRepository
                .findByIdAndStatus(commentId, CommentStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        return CommentResponse.CommentDetailResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getUser().getName())
                .likeCount(comment.getLikeCount())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    @Transactional
    public CommentResponse.CommentLikeResponse likeComment(Long commentId, Long userId) {

        Comment comment = getComment(commentId);

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

