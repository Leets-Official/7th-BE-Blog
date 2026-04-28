package com.leets.blog.entity;

import com.leets.blog.entity.enums.ContentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Comment extends BaseEntity implements Reportable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikes;

    // --- 신고 시스템  ---
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ContentStatus status = ContentStatus.ACTIVE;

    @Builder.Default
    private int totalWeight = 0;

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