package com.piebin.bingweb.features.file.domain;

import com.piebin.bingweb.features.file.common.FileType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String path;

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(nullable = false)
    private String ext;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType type;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void update(String originName, String ext, FileType type) {
        this.originName = originName;
        this.ext = ext;
        this.type = type;
        this.updatedAt = LocalDateTime.now();
    }
}
