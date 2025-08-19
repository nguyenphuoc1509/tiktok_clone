package com.phuocnt.tiktok.repository;

import com.phuocnt.tiktok.entity.Video;
import com.phuocnt.tiktok.enums.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {

    @Query("""
        SELECT v FROM Video v
        WHERE (:userId IS NULL OR v.user.userId = :userId)
          AND (:visibility IS NULL OR v.visibility = :visibility)
          AND (
                :q IS NULL OR :q = '' OR
                lower(v.title) LIKE lower(concat('%', :q, '%')) OR
                lower(v.description) LIKE lower(concat('%', :q, '%'))
              )
        """)
    Page<Video> search(
            @Param("userId") UUID userId,
            @Param("visibility") Visibility visibility,
            @Param("q") String q,
            Pageable pageable
    );
}
