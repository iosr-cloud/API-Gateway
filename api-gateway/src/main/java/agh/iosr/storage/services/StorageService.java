package agh.iosr.storage.services;

import java.net.URL;

public interface StorageService {
    void downloadFile(String filename);
    URL uploadFile(String filename, String uploadFilePath);
}