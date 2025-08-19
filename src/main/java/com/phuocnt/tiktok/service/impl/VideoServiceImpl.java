package com.phuocnt.tiktok.service.impl;

import com.phuocnt.tiktok.dto.request.VideoRequest;
import com.phuocnt.tiktok.dto.request.VideoUpdateRequest;
import com.phuocnt.tiktok.dto.response.VideoResponse;
import com.phuocnt.tiktok.entity.Video;
import com.phuocnt.tiktok.enums.Visibility;
import com.phuocnt.tiktok.exception.AppException;
import com.phuocnt.tiktok.exception.ErrorCode;
import com.phuocnt.tiktok.mapper.VideoMapper;
import com.phuocnt.tiktok.repository.UserRepository;
import com.phuocnt.tiktok.repository.VideoRepository;
import com.phuocnt.tiktok.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoMapper videoMapper;

    @Transactional
    @Override
    public VideoResponse create(VideoRequest req) {
        var owner = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "User not found"));

        Video v = videoMapper.toEntity(req, owner); // MapStruct gán field + owner
        videoRepository.save(v);
        return videoMapper.toResponse(v);
    }

    @Override
    public Page<VideoResponse> list(UUID userId, Visibility visibility, String q, Pageable pageable) {
        return videoRepository.search(userId, visibility, q, pageable)
                .map(videoMapper::toResponse);
    }

    @Override
    public VideoResponse get(UUID id) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Video not found"));
        return videoMapper.toResponse(v);
    }

    @Transactional
    @Override
    public VideoResponse update(UUID id, VideoUpdateRequest req) {
        Video v = videoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Video not found"));

        videoMapper.updateEntity(req, v);  // cập nhật in-place, không đổi owner/idD
        return videoMapper.toResponse(v);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        if (!videoRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOT_FOUND, "Video not found");
        }
        videoRepository.deleteById(id);
    }
}

