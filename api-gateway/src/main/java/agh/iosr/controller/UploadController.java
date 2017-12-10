package agh.iosr.controller;

import agh.iosr.storage.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile uploadedFile, @RequestParam("userId") String userId){
        uploadService.uploadFile(userId, uploadedFile);
        return ResponseEntity.ok().build();
    }

}
