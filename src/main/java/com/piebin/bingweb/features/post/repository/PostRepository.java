package com.piebin.bingweb.features.post.repository;

import com.piebin.bingweb.features.post.common.PostType;
import com.piebin.bingweb.features.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdx(Long idx);

    Page<Post> findAllByType(PostType type, Pageable pageable);
}
