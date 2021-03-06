package agh.iosr.controller;

import agh.iosr.model.VideoData;
import agh.iosr.storage.services.FileManagementService;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final FileManagementService fileManagementService;

    @GetMapping("/{id}")
    public ResponseEntity download(@PathVariable("id") long fileId){

        log.info("Downloading file id: " + fileId);

        Optional<S3Object> object = fileManagementService.downloadFile(fileId);

        if(object.isPresent()){

            S3Object s3Object = object.get();
            long contentLength = s3Object.getObjectMetadata().getContentLength();

            InputStream is = s3Object.getObjectContent();
            InputStreamResource resource = new InputStreamResource(is);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ s3Object.getKey())
                    .header("Content-Type","video/x-flv")
                    .contentLength(contentLength)
                    .body(resource);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Boolean> getStatus(@PathVariable("id") Long fileId){

        log.info("Getting status of file id: " + fileId);
        return ResponseEntity.ok(
                fileManagementService.isFileConverted(fileId)
        );
    }

    @PostMapping
    public ResponseEntity<VideoData> upload(@RequestParam("file") MultipartFile uploadedFile,
                                            @RequestParam("userId") String userId){

        log.info("Uploading file for userId: " + userId);
        log.info("File name: " + uploadedFile.getOriginalFilename());
        VideoData data = fileManagementService.uploadFile(userId, uploadedFile);
        return ResponseEntity.ok(data);
    }

}
