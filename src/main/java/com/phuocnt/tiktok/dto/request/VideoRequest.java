package com.phuocnt.tiktok.dto.request;

import com.phuocnt.tiktok.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequest {
    @NotNull(message = "userId is required")
    private UUID userId;

    @NotBlank
    @Size(max = 150)
    private String title;

    @Size(max = 2000)
    private String description;

    @NotBlank
    @Size(max = 1024)
    private String videoUrl;

    @Size(max = 1024)
    private String thumbnailUrl;

    @NotNull
    @Positive
    private Integer durationSeconds;

    @Builder.Default
    private List<@Size(max = 50) String> tags = List.of();

    @NotNull
    private Visibility visibility;
}
