package com.geocoding.geocoding;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Resource> download(String resultFilePath) {
        FileSystemResource resource = new FileSystemResource(resultFilePath);
        
        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        
        ContentDisposition disposition = ContentDisposition
                .attachment() // or  .inline() 
                .filename("geocoded_addresses.csv")
                .build();
        headers.setContentDisposition(disposition);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    // Getters
    public String getuploadPath(){
        return uploadPath;
    }

    public String getfileName(){
        return fileName;
    }

}