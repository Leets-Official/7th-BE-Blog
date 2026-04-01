package com.example.leets_7th.domain.post.entity;

import com.example.leets_7th.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "image")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url",nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
