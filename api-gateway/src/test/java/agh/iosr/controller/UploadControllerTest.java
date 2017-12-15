package agh.iosr.controller;

import agh.iosr.storage.services.FileManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UploadControllerTest {

    @Mock
    private FileManagementService fileService;

    @InjectMocks
    private UploadController uploadController;


    @Test
    public void invokeDownload() throws Exception {

        //given
        final long id = 1L;

        //when
        when(fileService.downloadFile(id)).thenReturn(Optional.empty());
        uploadController.download(id);

        //then
        Mockito.verify(fileService).downloadFile(id);
    }

    @Test
    public void invokeGetStatus() throws Exception {

        //given
        final long id = 1L;

        //when
        uploadController.getStatus(id);

        //then
        Mockito.verify(fileService).isFileConverted(id);
    }

    @Test
    public void invokeUpload() throws Exception {

        //given
        final MultipartFile file = mock(MultipartFile.class);
        final String userId = "userId";

        //when
        uploadController.upload(file, userId);

        //then
        Mockito.verify(fileService).uploadFile(userId, file);

    }

}