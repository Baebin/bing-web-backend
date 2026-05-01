package com.piebin.bingweb.features.file.repository;

import com.piebin.bingweb.features.file.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    Optional<FileInfo> findByPathAndName(String path, String originName);
}
