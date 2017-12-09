package agh.iosr.storage.services;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

public interface StorageService {
    void downloadFile(String filename);
    URL uploadFile(String filename, MultipartFile uploadFile);
}