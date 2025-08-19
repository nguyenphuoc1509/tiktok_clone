package com.phuocnt.tiktok.service;

import com.phuocnt.tiktok.dto.request.VideoRequest;
import com.phuocnt.tiktok.dto.request.VideoUpdateRequest;
import com.phuocnt.tiktok.dto.response.VideoResponse;
import com.phuocnt.tiktok.enums.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VideoService {
    VideoResponse create(VideoRequest req);
    Page<VideoResponse> list(UUID userId, Visibility visibility, String q, Pageable pageable);
    VideoResponse get(UUID id);
    VideoResponse update(UUID id, VideoUpdateRequest req);
    void delete(UUID id);
}
