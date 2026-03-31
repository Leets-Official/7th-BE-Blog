package com.example.demo.post.repository;

import com.example.demo.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p " +
            "join fetch p.blocks b " +
            "left join fetch b.media " +
            "where p.id = :id")
    Optional<Post> findPostWithBlocks(@Param("id") Long id);
}