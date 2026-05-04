package com.example.week2.comment.repository;

import com.example.week2.comment.entity.Comment;
import com.example.week2.comment.entity.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndStatus(
            Long Id,
            CommentStatus status
    );
}