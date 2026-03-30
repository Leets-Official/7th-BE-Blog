package com.example.leets_exercise1.domain.post;

import com.example.leets_exercise1.domain.media.Media;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_block")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "block_type", nullable = false, length = 20)
    private BlockType blockType;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "text_content", columnDefinition = "TEXT")
    private String textContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_id")
    private Media media;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public PostBlock(Post post, BlockType blockType, Integer sortOrder, String textContent, Media media) {
        this.post = post;
        this.blockType = blockType;
        this.sortOrder = sortOrder;
        this.textContent = textContent;
        this.media = media;
    }
}