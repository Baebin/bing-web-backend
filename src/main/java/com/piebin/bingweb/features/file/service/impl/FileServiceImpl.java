package com.piebin.bingweb.features.file.service.impl;

import com.piebin.bingweb.features.file.service.FileService;
import com.piebin.bingweb.features.file.common.FileType;
import com.piebin.bingweb.features.file.domain.FileInfo;
import com.piebin.bingweb.features.file.dto.internal.FileDto;
import com.piebin.bingweb.global.exception.CustomException;
import com.piebin.bingweb.features.file.exception.FileException;
import com.piebin.bingweb.features.file.repository.FileInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileInfoRepository fileInfoRepository;

    @Override
    public FileType getType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) return FileType.ETC;
        if (contentType.startsWith("image")) return FileType.IMAGE;
        if (contentType.startsWith("video")) return FileType.VIDEO;
        if (contentType.contains("pdf") || contentType.contains("msword") || contentType.contains("officedocument")) return FileType.DOCUMENT;
        return FileType.ETC;
    }

    @Override
    @Transactional
    public void upload(MultipartFile file, FileDto dto) {
        String ext = getExtension(file);
        FileType type = getType(file);
        Path targetLocation = getTargetLocation(dto.path(), dto.name(), ext, type);

        try {
            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("파일 저장 완료: {}", targetLocation);

            FileInfo fileInfo = fileInfoRepository.findByPathAndName(
                    dto.path(),
                    dto.name()
            ).orElseGet(() -> FileInfo.builder()
                    .path(dto.path())
                    .name(dto.name())
                    .build());
            fileInfo.update(file.getOriginalFilename(), ext, type);
            fileInfoRepository.save(fileInfo);
        } catch (IOException e) {
            log.error("파일 저장 실패: {}", dto.name(), e);
            throw new CustomException(FileException.FILE_STORAGE_FAILED);
        }
    }

    private Resource download(FileInfo fileInfo) {
        try {
            Path filePath = getTargetLocation(
                    fileInfo.getPath(),
                    fileInfo.getName(),
                    fileInfo.getExt(),
                    fileInfo.getType()
            );
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else throw new CustomException(FileException.FILE_NOT_FOUND);
        } catch (MalformedURLException e) {
            throw new CustomException(FileException.FILE_NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Resource download(FileDto dto) {
        FileInfo fileInfo = fileInfoRepository.findByPathAndName(
                dto.path(), dto.name()
        ).orElseThrow(() -> new CustomException(FileException.FILE_NOT_FOUND));
        return download(fileInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> downloadResponse(FileDto dto) {
        FileInfo fileInfo = fileInfoRepository.findByPathAndName(
                dto.path(), dto.name()
        ).orElseThrow(() -> new CustomException(FileException.FILE_NOT_FOUND));

        Resource resource = download(fileInfo);
        String contentDisposition = ContentDisposition.attachment()
                .filename(fileInfo.getOriginName(), StandardCharsets.UTF_8)
                .build()
                .toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(resource);
    }

    private Path getTargetLocation(String subPath, String name, String ext, FileType type) {
        return Paths.get(System.getProperty("user.dir"))
                .resolve(type.getDirectory())
                .resolve(subPath)
                .resolve(name + "." + ext)
                .normalize();
    }

    private String getExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return StringUtils.getFilenameExtension(originalFilename);
    }
}
