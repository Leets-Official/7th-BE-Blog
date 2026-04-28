package com.example.leets7th.domain.comment.repository;

import com.example.leets7th.domain.comment.entity.Comment;
import com.example.leets7th.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostAndParentIsNullOrderByCreatedAtAsc(Post post);
    List<Comment> findAllByParentOrderByCreatedAtAsc(Comment parent);
}
