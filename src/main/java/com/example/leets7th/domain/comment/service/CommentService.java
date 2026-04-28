package com.example.leets7th.domain.comment.service;

import com.example.leets7th.domain.comment.entity.Comment;
import com.example.leets7th.domain.comment.repository.CommentRepository;
import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.domain.post.repository.PostRepository;
import com.example.leets7th.global.exception.AlreadyAdoptedException;
import com.example.leets7th.global.exception.CommentNotFoundException;
import com.example.leets7th.global.exception.ForbiddenException;
import com.example.leets7th.global.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

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
            throw new IllegalArgumentException("해당 게시글에 속한 댓글이 아닙니다.");
        }

        // 이미 채택된 댓글이 있는지 확인
        if (commentRepository.existsByPostIdAndAdoptedTrue(postId)) {
            throw new AlreadyAdoptedException();
        }

        comment.adopt();
    }
}
