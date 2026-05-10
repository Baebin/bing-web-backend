package com.piebin.bingweb.features.post.repository;

import com.piebin.bingweb.features.post.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdx(Long idx);
    Optional<Comment> findByIdxAndPostIdx(Long idx, Long postIdx);

    @Query("""
        select c from Comment c
        join fetch c.author
        where c.post.idx = :postIdx
            and c.parent is null
            and (:lastIdx is null or c.idx > :lastIdx)
        order by c.idx asc
    """)
    List<Comment> findParents(@Param("postIdx") Long postIdx,
                              @Param("lastIdx") Long lastIdx,
                              Pageable pageable);

    @Query("""
        select c from Comment c
        join fetch c.author
        where c.post.idx = :postIdx 
            and c.parent.idx = :parentIdx
        order by c.idx asc
    """)
    List<Comment> findChildren(@Param("postIdx") Long postIdx,
                               @Param("parentIdx") Long parentIdx);
}
