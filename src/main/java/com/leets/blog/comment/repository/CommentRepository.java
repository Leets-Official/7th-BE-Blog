package com.leets.blog.comment.repository;

import com.leets.blog.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByPostIdAndAcceptedTrue(Long postId);
    List<Comment> findAllByPostIdOrderByIdAsc(Long postId);
}
