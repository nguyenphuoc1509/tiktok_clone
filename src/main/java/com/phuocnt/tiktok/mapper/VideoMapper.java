package com.phuocnt.tiktok.mapper;

import com.phuocnt.tiktok.dto.request.VideoRequest;
import com.phuocnt.tiktok.dto.request.VideoUpdateRequest;
import com.phuocnt.tiktok.dto.response.VideoResponse;
import com.phuocnt.tiktok.entity.User;
import com.phuocnt.tiktok.entity.Video;
import org.mapstruct.*;
import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VideoMapper {

    /* ---------- Entity -> Response ---------- */
    @Mapping(source = "user.userId", target = "userId")
    VideoResponse toResponse(Video entity);

    List<VideoResponse> toResponseList(List<Video> entities);

    /* ---------- Request -> Entity (create) ---------- */
    // user sẽ được set từ service, không map trực tiếp userId -> User ở đây
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true) // set bằng tham số owner
    @Mapping(target = "createdAt", ignore = true)
    Video toEntity(VideoRequest req);

    /* Helper ghép owner vào entity mới tạo (create) */
    @AfterMapping
    default void setOwner(@MappingTarget Video video, @Context User owner) {
        video.setUser(owner);
    }

    /* API tiện lợi: tạo entity đầy đủ từ req + owner */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    default Video toEntity(VideoRequest req, @Context User owner) {
        Video v = toEntity(req);
        setOwner(v, owner);
        return v;
    }

    /* ---------- Update: req -> entity hiện có ---------- */
    @Mapping(target = "user", ignore = true)      // không cho đổi owner ở update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(VideoUpdateRequest req, @MappingTarget Video entity);
}
