package com.example.demo.domain.comment.repository;

import com.example.demo.domain.comment.entity.Comment;
import com.example.demo.domain.comment.entity.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByPostIdAndStatus(Long postId, CommentStatus status);
}
