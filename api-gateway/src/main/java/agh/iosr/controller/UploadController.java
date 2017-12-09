package agh.iosr.controller;

import agh.iosr.storage.services.S3StorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private S3StorageService s3StorageService;

    @Value("${s3.filepath}")
    private String uploadFilePath;

    @Value("${s3.filename}")
    private String filename;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile uploadedFile){
        URL fileUrl = s3StorageService.uploadFile(filename, uploadedFile);
        return ResponseEntity.ok(fileUrl.toString());
    }

}
