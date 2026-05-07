package com.leets.blog.entity;

import com.leets.blog.entity.enums.ContentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder // 상속 구조에는 SuperBuilder가 필수입니다
public class Post extends BaseEntity implements Reportable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // TEXT 타입이 더 안전합니다
    private String content;

    @Column(name = "image", length = 255)
    private String image;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLikes;

    // --- 신고 시스템 (Reportable) ---
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private ContentStatus status = ContentStatus.ACTIVE;

    @Column(name = "total_weight")
    @Builder.Default
    private int totalWeight = 0;

    // --- 비즈니스 로직 ---
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = java.time.LocalDateTime.now();
    }

    // --- Reportable 인터페이스 구현 ---
    @Override
    public void addWeight(int weight) {
        this.totalWeight += weight;
    }

    @Override
    public int getTotalWeight() {
        return this.totalWeight;
    }

    @Override
    public void updateStatus(ContentStatus status) {
        this.status = status;
    }
}