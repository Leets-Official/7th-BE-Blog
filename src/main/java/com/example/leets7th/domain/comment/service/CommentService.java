package com.example.leets7th.domain.comment.service;

import com.example.leets7th.domain.comment.dto.req.CommentRequestDTO;
import com.example.leets7th.domain.comment.dto.res.CommentResponseDTO;

import java.util.List;
import com.example.leets7th.domain.comment.entity.Comment;
import com.example.leets7th.domain.comment.exception.CommentException;
import com.example.leets7th.domain.comment.exception.code.CommentErrorCode;
import com.example.leets7th.domain.comment.repository.CommentRepository;
import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.domain.post.exception.PostException;
import com.example.leets7th.domain.post.exception.code.PostErrorCode;
import com.example.leets7th.domain.post.repository.PostRepository;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.apiPayload.code.GeneralErrorCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentResponseDTO.CommentResDTO> getCommentList(Long userId, Long postId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.UNAUTHORIZED));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (post.getIsReported()) {
            throw new PostException(PostErrorCode.POST_REPORTED);
        }

        return commentRepository.findAllByPostAndParentIsNullOrderByCreatedAtAsc(post).stream()
                .map(comment -> {
                    List<CommentResponseDTO.ReplyResDTO> replies =
                            commentRepository.findAllByParentOrderByCreatedAtAsc(comment).stream()
                                    .map(reply -> CommentResponseDTO.ReplyResDTO.builder()
                                            .commentId(reply.getId())
                                            .content(reply.getContent())
                                            .nickname(reply.getUser().getNickname())
                                            .createdAt(reply.getCreatedAt())
                                            .build())
                                    .toList();

                    return CommentResponseDTO.CommentResDTO.builder()
                            .commentId(comment.getId())
                            .content(comment.getContent())
                            .nickname(comment.getUser().getNickname())
                            .createdAt(comment.getCreatedAt())
                            .replies(replies)
                            .build();
                })
                .toList();
    }

    public CommentResponseDTO.CreateCommentResDTO createComment(
            Long userId, Long postId, CommentRequestDTO.CreateCommentDTO req) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.UNAUTHORIZED));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (post.getIsReported()) {
            throw new PostException(PostErrorCode.POST_REPORTED);
        }

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(req.content())
                .build();

        Comment saved = commentRepository.save(comment);

        return CommentResponseDTO.CreateCommentResDTO.builder()
                .commentId(saved.getId())
                .content(saved.getContent())
                .nickname(saved.getUser().getNickname())
                .createdAt(saved.getCreatedAt())
                .build();
    }


    public CommentResponseDTO.CreateCommentResDTO createReply(
            Long userId, Long postId, Long parentCommentId, CommentRequestDTO.CreateCommentDTO req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.UNAUTHORIZED));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (post.getIsReported()) {
            throw new PostException(PostErrorCode.POST_REPORTED);
        }

        Comment parent = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        if (!parent.getPost().getId().equals(postId)) {
            throw new CommentException(CommentErrorCode.COMMENT_POST_MISMATCH);
        }

        if (parent.getParent() != null) {
            throw new CommentException(CommentErrorCode.REPLY_NOT_ALLOWED);
        }

        Comment reply = Comment.builder()
                .user(user)
                .post(post)
                .parent(parent)
                .content(req.content())
                .build();

        Comment saved = commentRepository.save(reply);

        return CommentResponseDTO.CreateCommentResDTO.builder()
                .commentId(saved.getId())
                .content(saved.getContent())
                .nickname(saved.getUser().getNickname())
                .createdAt(saved.getCreatedAt())
                .build();
    }
}
