package com.piebin.bingweb.features.post.domain;

import com.piebin.bingweb.features.post.dto.internal.CommentDto;
import com.piebin.bingweb.features.post.exception.CommentException;
import com.piebin.bingweb.global.domain.Account;
import com.piebin.bingweb.global.exception.CustomException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_idx", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_idx", nullable = false)
    private Account author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_idx")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("idx desc")
    private List<Comment> children = new ArrayList<>();

    @Column(nullable = false, length = 200)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Comment from(CommentDto dto, Post post, Account author, Comment parent) {
        if (parent != null && !parent.getPost().getIdx().equals(post.getIdx()))
            throw new CustomException(CommentException.INVALID_PARENT_POST);
        return Comment.builder()
                .post(post)
                .author(author)
                .parent(parent)
                .content(dto.content())
                .build();
    }
}
