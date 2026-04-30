package com.piebin.bingweb.features.file.service;

import com.piebin.bingweb.features.file.common.FileType;
import com.piebin.bingweb.features.file.dto.internal.FileDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileType getType(MultipartFile file);
    void upload(MultipartFile file, FileDto dto);
    Resource download(FileDto dto);
    ResponseEntity<Resource> downloadResponse(FileDto dto);
}
