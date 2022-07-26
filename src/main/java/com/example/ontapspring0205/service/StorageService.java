package com.example.ontapspring0205.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        try {
            Files.write(Paths.get(path + fileName), file.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
