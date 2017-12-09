package agh.iosr.controller;

import agh.iosr.storage.services.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private S3StorageService s3StorageService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile uploadedFile){
        URL fileUrl = s3StorageService.uploadFile(uploadedFile.getOriginalFilename(), uploadedFile);
        return ResponseEntity.ok().build();
    }

}
