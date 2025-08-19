package com.phuocnt.tiktok.dto.request;

import com.phuocnt.tiktok.enums.Visibility;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VideoUpdateRequest {

    @NotBlank @Size(max = 150)
    private String title;

    @Size(max = 2000)
    private String description;

    @NotBlank @Size(max = 1024)
    private String videoUrl;

    @Size(max = 1024)
    private String thumbnailUrl;

    @NotNull @Positive
    private Integer durationSeconds;

    @Builder.Default
    private List<@Size(max = 50) String> tags = List.of();

    @NotNull
    private Visibility visibility;
}
