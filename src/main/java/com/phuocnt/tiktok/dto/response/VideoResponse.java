package com.phuocnt.tiktok.dto.response;

import com.phuocnt.tiktok.enums.Visibility;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VideoResponse {
    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private Integer durationSeconds;
    private List<String> tags;
    private Visibility visibility;
    private Instant createdAt;
}
