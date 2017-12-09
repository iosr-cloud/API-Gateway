package agh.iosr.controller;

import agh.iosr.storage.services.S3Service;
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
    S3Service s3Service;

    @Value("${jsa.s3.uploadfile}")
    private String uploadFilePath;

    @Value("${jsa.s3.key}")
    private String key;

    @GetMapping
    public ResponseEntity<String> upload(){
        System.out.println("---------------- START UPLOAD FILE ----------------");
        URL fileUrl = s3Service.uploadFile(key, uploadFilePath);

        return ResponseEntity.ok(fileUrl.toString());
    }

}
