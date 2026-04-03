package com.example.leets_exercise1.repository;

import com.example.leets_exercise1.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}