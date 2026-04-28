package com.example.demo.domain.comment.service;

import com.example.demo.domain.comment.dto.CommentCreateRequest;
import com.example.demo.domain.comment.dto.CommentCreateResponse;
import com.example.demo.domain.comment.dto.CommentStatusResponse;
import com.example.demo.domain.comment.entity.Comment;
import com.example.demo.domain.comment.entity.CommentStatus;
import com.example.demo.domain.comment.repository.CommentRepository;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.entity.PostStatus;
import com.example.demo.domain.post.repository.PostRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentCreateResponse createComment(Long postId, CommentCreateRequest request) {
        Post post = findPost(postId);
        User user = findUser(request.userId());

        if (post.getStatus() == PostStatus.HIDDEN) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "POST_HIDDEN", "숨김 처리된 게시글에는 댓글을 작성할 수 없습니다.");
        }

        Comment comment = commentRepository.save(Comment.of(user, post, request.content()));
        return new CommentCreateResponse(comment.getId(), comment.getStatus());
    }

    @Transactional
    public CommentStatusResponse adoptComment(Long commentId, Long userId) {
        Comment comment = findComment(commentId);
        Post post = comment.getPost();

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "FORBIDDEN", "댓글 채택 권한이 없습니다.");
        }
        if (post.getStatus() == PostStatus.HIDDEN) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "POST_HIDDEN", "숨김 처리된 게시글에서는 댓글을 채택할 수 없습니다.");
        }
        if (comment.getStatus() == CommentStatus.HIDDEN) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "COMMENT_HIDDEN", "숨김 처리된 댓글은 채택할 수 없습니다.");
        }
        if (commentRepository.existsByPostIdAndStatus(post.getId(), CommentStatus.ADOPTED)) {
            throw new CustomException(HttpStatus.CONFLICT, "COMMENT_ALREADY_ADOPTED", "이미 채택된 댓글이 있습니다.");
        }

        comment.adopt();
        return new CommentStatusResponse(comment.getId(), comment.getStatus());
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "해당 댓글을 찾을 수 없습니다."));
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
