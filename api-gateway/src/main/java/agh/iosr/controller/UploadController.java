package agh.iosr.controller;

import agh.iosr.event.api.EventSender;
import agh.iosr.event.model.EventMessage;
import agh.iosr.model.VideoConversionType;
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

    @Autowired
    private EventSender eventSender;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile uploadedFile){
        URL fileUrl = s3StorageService.uploadFile(uploadedFile.getOriginalFilename(), uploadedFile);
        //put message to queue
        eventSender.sendEvent(new EventMessage(fileUrl.toString(), VideoConversionType.TWO_TIMES_FASTER));
        //put to db
        return ResponseEntity.ok().build();
    }

}
