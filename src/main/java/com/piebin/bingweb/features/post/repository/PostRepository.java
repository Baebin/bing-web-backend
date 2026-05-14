package com.piebin.bingweb.features.post.repository;

import com.piebin.bingweb.features.post.common.PostType;
import com.piebin.bingweb.features.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdx(Long idx);

    Page<Post> findAllByType(PostType type, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.idx = :idx")
    void incrementViewCount(@Param("idx") Long idx);
}
