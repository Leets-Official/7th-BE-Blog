package com.example.leets7th.domain.comment.service;

import com.example.leets7th.domain.comment.dto.CommentCreateRequest;
import com.example.leets7th.domain.comment.entity.Comment;
import com.example.leets7th.domain.comment.repository.CommentRepository;
import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.domain.post.repository.PostRepository;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.exception.AlreadyAdoptedException;
import com.example.leets7th.global.exception.CommentNotFoundException;
import com.example.leets7th.global.exception.ForbiddenException;
import com.example.leets7th.global.exception.InvalidCommentPostException;
import com.example.leets7th.global.exception.PostNotFoundException;
import com.example.leets7th.global.exception.UserNotFoundException;
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
    public Long createComment(Long postId, CommentCreateRequest request, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Comment comment = Comment.create(request.content(), user, post);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void adoptComment(Long postId, Long commentId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        // 게시글 작성자만 채택 가능
        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenException("게시글 작성자만 댓글을 채택할 수 있습니다.");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        // 해당 게시글의 댓글인지 확인
        if (!comment.getPost().getId().equals(postId)) {
            throw new InvalidCommentPostException();
        }

        // 이미 채택된 댓글이 있는지 확인
        if (commentRepository.existsByPostIdAndAdoptedTrue(postId)) {
            throw new AlreadyAdoptedException();
        }

        comment.adopt();
    }
}
