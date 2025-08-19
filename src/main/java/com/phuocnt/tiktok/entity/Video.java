package com.phuocnt.tiktok.entity;

import com.phuocnt.tiktok.enums.Visibility;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "videos",
        indexes = {
                @Index(name = "idx_video_user", columnList = "user_id"),
                @Index(name = "idx_video_visibility", columnList = "visibility"),
                @Index(name = "idx_video_created_at", columnList = "created_at")
        })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Video {

    @Id
    @GeneratedValue
    @Column(name = "video_id", nullable = false, updatable = false)
    private UUID id;

    // FK -> users.id (giả sử User.id là UUID)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(name = "video_url", length = 1024, nullable = false)
    private String videoUrl;

    @Column(name = "thumbnail_url", length = 1024)
    private String thumbnailUrl;

    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

    @ElementCollection
    @CollectionTable(name = "video_tags", joinColumns = @JoinColumn(name = "video_id"))
    @Column(name = "tag", length = 50)
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Visibility visibility;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;
}
