package com.piebin.bingweb.features.post.repository;

import com.piebin.bingweb.features.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostIdxAndAccountIdx(Long postIdx, Long accountIdx);

    int countByPostIdx(Long postIdx);

    Optional<PostLike> findByPostIdxAndAccountIdx(Long postIdx, Long accountIdx);

    void deleteByPostIdxAndAccountIdx(Long postIdx, Long accountIdx);
}
