package com.example.week2.comment.repository;

import com.example.week2.comment.entity.Comment;
import com.example.week2.comment.entity.CommentLike;
import com.example.week2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository
        extends JpaRepository<CommentLike, Long> {

    boolean existsByCommentAndUser(Comment comment, User user);
}
