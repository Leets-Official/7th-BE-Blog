package com.example.demo.report.repository;

import com.example.demo.comment.entity.Comment;
import com.example.demo.post.entity.Post;
import com.example.demo.report.entity.Report;
import com.example.demo.report.entity.ReportStatus;
import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByReporterAndPostAndStatus(
            User reporter,
            Post post,
            ReportStatus status
    );

    boolean existsByReporterAndCommentAndStatus(
            User reporter,
            Comment comment,
            ReportStatus status
    );
}