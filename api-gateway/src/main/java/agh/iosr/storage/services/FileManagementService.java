package agh.iosr.storage.services;

import agh.iosr.event.api.EventSender;
import agh.iosr.event.model.EventMessage;
import agh.iosr.model.VideoConversionType;
import agh.iosr.model.VideoData;
import agh.iosr.repository.VideoDataRepository;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileManagementService {

    private final S3StorageService s3StorageService;
    private final EventSender eventSender;
    private final VideoDataRepository videoDataRepository;

    public boolean isFileConverted(long fileId){
        return videoDataRepository.findStatusById(fileId);
    }

    public Optional<S3Object> downloadFile(Long fileId){

        Optional<S3Object> file = Optional.empty();
        VideoData data = videoDataRepository.findOne(fileId);

        if(data != null && data.isStatus()){
            file = s3StorageService.downloadFile(data.getConvertedFilePath());
        }

        return file;
    }


    @Transactional
    public VideoData uploadFile(String userId, MultipartFile uploadedFile) {
        VideoConversionType videoConversionType = VideoConversionType.TWO_TIMES_FASTER;
        URL fileUrl = s3StorageService.uploadFile(userId +"/"+uploadedFile.getOriginalFilename(), uploadedFile);

        VideoData data = new VideoData(userId, uploadedFile.getOriginalFilename(), fileUrl.toString(), videoConversionType);
        videoDataRepository.save(data);

        eventSender.sendEvent(new EventMessage(data.getId(), fileUrl.toString(), videoConversionType));
        return data;
    }
}
