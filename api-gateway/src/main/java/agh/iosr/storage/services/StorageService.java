package agh.iosr.storage.services;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Optional;

public interface StorageService {
    Optional<S3Object> downloadFile(String filename);
    URL uploadFile(String filename, MultipartFile uploadFile);
}