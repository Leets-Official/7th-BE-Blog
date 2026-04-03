package com.example.leets_exercise1.domain.media;

import com.example.leets_exercise1.domain.post.PostBlock;
import com.example.leets_exercise1.domain.report.ReportBlock;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "media")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(nullable = false)
    private Integer size;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "media")
    private List<PostBlock> postBlocks = new ArrayList<>();

    @OneToMany(mappedBy = "media")
    private List<ReportBlock> reportBlocks = new ArrayList<>();

    @Builder
    public Media(String url, String originalName, String mimeType, Integer size) {
        this.url = url;
        this.originalName = originalName;
        this.mimeType = mimeType;
        this.size = size;
    }
}