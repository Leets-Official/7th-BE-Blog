package com.example.leets_exercise1.domain.report;

import com.example.leets_exercise1.domain.media.Media;
import com.example.leets_exercise1.domain.post.BlockType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_block")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

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
    public ReportBlock(Report report, BlockType blockType, Integer sortOrder, String textContent, Media media) {
        this.report = report;
        this.blockType = blockType;
        this.sortOrder = sortOrder;
        this.textContent = textContent;
        this.media = media;
    }
}