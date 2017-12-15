package agh.iosr.storage.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(URL.class)
public class S3StorageServiceTest {

    @Mock
    private AmazonS3 client;

    @InjectMocks
    private S3StorageService s3StorageService;

    @Test
    public void downloadFile() throws Exception {

        //given
        final String fileName = "filename";
        final String bucketName = "bucketname";
        final S3Object s3Object = mock(S3Object.class);
        ReflectionTestUtils.setField(s3StorageService, "bucketName", bucketName);

        //when
        when(client.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);
        S3Object result = s3StorageService.downloadFile(fileName).orElseGet(null);

        //then
        verify(client).getObject(any(GetObjectRequest.class));
        assertEquals(s3Object, result);
    }

    @Test
    public void uploadFile() throws Exception {

        //given
        final MultipartFile file = mock(MultipartFile.class);
        final InputStream is = mock(InputStream.class);
        final URL url = PowerMockito.mock(URL.class);
        final String fileName = "filename";
        final String bucketName = "bucketname";
        ReflectionTestUtils.setField(s3StorageService, "bucketName", bucketName);

        //when
        when(client.getUrl(bucketName, fileName)).thenReturn(url);
        when(file.getInputStream()).thenReturn(is);
        URL result = s3StorageService.uploadFile(fileName, file);

        //then
        verify(client, times(1)).putObject(any(PutObjectRequest.class));
        verify(client, times(1)).getUrl(bucketName, fileName);
        assertEquals(result, url);
    }
}
