package com.example.demo.post.entity;

import com.example.demo.media.entity.Media;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostBlock {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private BlockType blockType;

    private Integer sortOrder;

    @Column(columnDefinition = "TEXT")
    private String textContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Media media;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setPost(Post post) {
        this.post = post;
    }
}
