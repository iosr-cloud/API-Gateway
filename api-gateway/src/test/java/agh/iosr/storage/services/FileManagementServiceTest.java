package agh.iosr.storage.services;

import agh.iosr.event.api.EventSender;
import agh.iosr.event.model.EventMessage;
import agh.iosr.model.VideoConversionType;
import agh.iosr.model.VideoData;
import agh.iosr.repository.VideoDataRepository;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(URL.class)
public class FileManagementServiceTest {

    @Mock
    private S3StorageService s3StorageService;

    @Mock
    private EventSender eventSender;

    @Mock
    private VideoDataRepository videoDataRepository;

    @InjectMocks
    private FileManagementService fileManagementService;

    @Test
    public void isFileConverted() throws Exception {

        //given
        final long fileId = 1L;
        final boolean expected = true;

        //when
        when(videoDataRepository.findStatusById(fileId)).thenReturn(expected);
        boolean result = fileManagementService.isFileConverted(fileId);

        //then
        verify(videoDataRepository).findStatusById(fileId);
        assertEquals(expected, result);
    }

    @Test
    public void downloadFile() throws Exception {

        //given
        final VideoData data = mock(VideoData.class);
        final S3Object expected = mock(S3Object.class);
        final Long fileId = 2L;
        final String filePath = "user/test.txt";
        final String fileName = "test.txt";

        //when
        when(videoDataRepository.findOne(fileId)).thenReturn(data);
        when(data.isStatus()).thenReturn(true);
        when(data.getConvertedFilePath()).thenReturn(filePath);
        when(s3StorageService.downloadFile(fileName)).thenReturn(Optional.ofNullable(expected));
        S3Object result = fileManagementService.downloadFile(fileId).orElseGet(null);

        //then
        verify(videoDataRepository).findOne(fileId);
        verify(s3StorageService).downloadFile(fileName);
        assertEquals(expected, result);
    }

    @Test
    public void uploadFile() throws Exception {
        //given
        final URL url = PowerMockito.mock(URL.class);
        final String stringUrl = "url";
        final MultipartFile file = mock(MultipartFile.class);
        final String fileName = "fileName";
        final String userId = "userId";

        //when
        when(file.getName()).thenReturn(fileName);
        when(file.getOriginalFilename()).thenReturn(fileName);
        when(s3StorageService.uploadFile(userId + "/" + fileName, file)).thenReturn(url);
        when(url.toString()).thenReturn(stringUrl);
        VideoData result = fileManagementService.uploadFile(userId, file);

        //then
        verify(s3StorageService).uploadFile(userId + "/" + fileName, file);
        verify(videoDataRepository).save(any(VideoData.class));
        verify(eventSender).sendEvent(any(EventMessage.class));
        assertEquals(userId, result.getUserId());

    }

}