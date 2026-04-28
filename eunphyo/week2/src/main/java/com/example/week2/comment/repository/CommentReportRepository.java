package com.example.week2.comment.repository;

import com.example.week2.comment.entity.Comment;
import com.example.week2.comment.entity.CommentReport;
import com.example.week2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    boolean existsByCommentAndReporter(Comment comment, User reporter);
}