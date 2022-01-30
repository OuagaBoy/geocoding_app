package com.geocoding.geocoding;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HttpFileService {

    private String uploadPath = "/Users/ouagaboy/Documents/Scratch/uploads/";
    private String fileName;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public void save(MultipartFile file) throws Exception {
        fileName = file.getOriginalFilename() + UUID.randomUUID();
        try {
            Path uploadDirectory = Paths.get(uploadPath);
            if (!Files.exists(uploadDirectory)) {
                init();
            }
            Path filePath = Paths.get(uploadPath, fileName);
            Files.copy(file.getInputStream(), filePath);
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    public Resource load() {
        UrlResource resource = null;
        try {
            Path file = Paths.get(uploadPath + fileName);
            resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("The file doesn't exist!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return resource;
    }

    // Getters
    public String getuploadPath(){
        return uploadPath;
    }

    public String getfileName(){
        return fileName;
    }

}