package com.example.leets7th.domain.comment.repository;

import com.example.leets7th.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
