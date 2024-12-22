package dev.matheushenrique.sgpa.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String uploadFile(MultipartFile file, String fileName) throws IOException;
    String getUrlFile(String fileName);
    String deleteFile(String fileName);
}
