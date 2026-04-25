package com.leets.blog.domain.report.repository;

import com.leets.blog.domain.comment.entity.Comment;
import com.leets.blog.domain.report.entity.CommentReport;
import com.leets.blog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    boolean existsByUserAndComment(User user, Comment comment);
}
