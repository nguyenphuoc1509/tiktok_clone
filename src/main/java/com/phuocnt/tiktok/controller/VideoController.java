package com.phuocnt.tiktok.controller;

import com.phuocnt.tiktok.dto.request.VideoRequest;
import com.phuocnt.tiktok.dto.request.VideoUpdateRequest;
import com.phuocnt.tiktok.dto.response.VideoResponse;
import com.phuocnt.tiktok.enums.Visibility;
import com.phuocnt.tiktok.dto.response.ApiResponse;
import com.phuocnt.tiktok.exception.ErrorCode;
import com.phuocnt.tiktok.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoController {

    VideoService videoService;

    @PostMapping
    public ResponseEntity<ApiResponse<VideoResponse>> create(@Valid @RequestBody VideoRequest req) {
        var data = videoService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(ErrorCode.CREATED, data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VideoResponse>> get(@PathVariable UUID id) {
        var data = videoService.get(id);
        return ResponseEntity.ok(ApiResponse.of( ErrorCode.OK, data));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<VideoResponse>>> list(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) Visibility visibility,
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        var data = videoService.list(userId, visibility, q, pageable);
        return ResponseEntity.ok(ApiResponse.of( ErrorCode.OK, data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VideoResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody VideoUpdateRequest req
    ) {
        var data = videoService.update(id, req);
        return ResponseEntity.ok(ApiResponse.of( ErrorCode.OK, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        videoService.delete(id);
        return ResponseEntity.ok(ApiResponse.of( ErrorCode.OK, null));
    }
}
