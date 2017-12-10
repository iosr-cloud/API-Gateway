package agh.iosr.storage.services;

import agh.iosr.event.api.EventSender;
import agh.iosr.event.model.EventMessage;
import agh.iosr.model.VideoConversionType;
import agh.iosr.model.VideoData;
import agh.iosr.repository.VideoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.net.URL;

@Service
public class UploadService {

    @Autowired
    private S3StorageService s3StorageService;

    @Autowired
    private EventSender eventSender;

    @Autowired
    private VideoDataRepository videoDataRepository;

    @Transactional
    public void uploadFile(String userId, MultipartFile uploadedFile) {
        VideoConversionType videoConversionType = VideoConversionType.TWO_TIMES_FASTER;
        URL fileUrl = s3StorageService.uploadFile(userId +"/"+uploadedFile.getOriginalFilename(), uploadedFile);

        eventSender.sendEvent(new EventMessage(userId, fileUrl.toString(), videoConversionType));

        VideoData data = new VideoData(userId, uploadedFile.getOriginalFilename(), fileUrl.toString(), videoConversionType);
        videoDataRepository.save(data);
    }
}
