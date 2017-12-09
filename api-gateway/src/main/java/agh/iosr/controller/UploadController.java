package agh.iosr.controller;

import agh.iosr.storage.services.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URL;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private S3StorageService s3StorageService;

    @Value("${jsa.s3.filepath}")
    private String uploadFilePath;

    @Value("${jsa.s3.filename}")
    private String filename;

    @GetMapping
    public ResponseEntity<String> upload(){
        System.out.println("---------------- START UPLOAD FILE ----------------");
        URL fileUrl = s3StorageService.uploadFile(filename, uploadFilePath);

        return ResponseEntity.ok(fileUrl.toString());
    }

}
