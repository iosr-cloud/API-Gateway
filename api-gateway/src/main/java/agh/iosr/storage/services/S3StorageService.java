package agh.iosr.storage.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Override
    public Optional<S3Object> downloadFile(String filename) {

        S3Object s3object = null;
        try {

            log.info("Downloading an object");
            s3object = s3client.getObject(new GetObjectRequest(bucketName, filename));
            log.info("===================== Import File - Done! =====================");

        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException: ");
            log.info("Error Message: " + ace.getMessage());
        }
        return Optional.ofNullable(s3object);
    }

    @Override
    public URL uploadFile(String filename, MultipartFile uploadFile) {
        URL fileURL = null;
        try {
            s3client.putObject(new PutObjectRequest(bucketName, filename, uploadFile.getInputStream(), null).withCannedAcl(CannedAccessControlList.PublicRead));
            fileURL = s3client.getUrl(bucketName, filename);
            log.info("Uploaded File: " + filename);
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            log.info("Caught an AmazonClientException: ");
            log.info("Error Message: " + ace.getMessage());
        } catch (IOException e) {
            log.info("File Transform Error");
        }
        return fileURL;
    }
}
