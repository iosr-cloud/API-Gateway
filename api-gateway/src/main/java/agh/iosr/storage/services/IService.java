package agh.iosr.storage.services;

import java.net.URL;

public interface IService {
    void downloadFile(String keyName);
    URL uploadFile(String keyName, String uploadFilePath);
}