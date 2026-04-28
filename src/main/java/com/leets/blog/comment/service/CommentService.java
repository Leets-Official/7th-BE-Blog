package com.leets.blog.comment.service;

import com.leets.blog.comment.domain.Comment;
import com.leets.blog.comment.dto.CommentRequest;
import com.leets.blog.comment.dto.CommentResponse;
import com.leets.blog.comment.repository.CommentRepository;
import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.post.domain.Post;
import com.leets.blog.post.repository.PostRepository;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import com.leets.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse create(AuthUser authUser, Long postId, CommentRequest.Create request) {
        User user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        Comment comment = new Comment(request.getContent(), post, user);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponse(savedComment);
    }

    @Transactional
    public CommentResponse accept(AuthUser authUser, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_NOT_FOUND));

        boolean isPostAuthor = comment.getPost().getUser() != null
                && comment.getPost().getUser().getId().equals(authUser.getUserId());
        boolean isAdmin = authUser.getRole() == UserRole.ADMIN;
        if (!isPostAuthor && !isAdmin) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        if (commentRepository.existsByPostIdAndAcceptedTrue(comment.getPost().getId())) {
            throw new BusinessException(ErrorCode.ALREADY_ACCEPTED_COMMENT_EXISTS);
        }

        comment.accept();
        return new CommentResponse(comment);
    }
}
