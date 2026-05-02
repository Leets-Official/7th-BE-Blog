package com.example.leets7th.domain.comment.repository;

import com.example.leets7th.domain.comment.entity.Comment;
import com.example.leets7th.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.post = :post AND c.parent IS NULL ORDER BY c.isAdopted DESC, c.createdAt ASC")
    List<Comment> findAllByPostAndParentIsNull(@Param("post") Post post);

    List<Comment> findAllByParentOrderByCreatedAtAsc(Comment parent);

    boolean existsByPostAndIsAdoptedTrue(Post post);
}
