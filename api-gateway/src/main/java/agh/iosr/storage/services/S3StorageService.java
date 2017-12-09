package agh.iosr.storage.services;

import java.io.File;
import java.net.URL;

import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3StorageService implements StorageService {

    private Logger logger = LoggerFactory.getLogger(S3StorageService.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${jsa.s3.bucket}")
    private String bucketName;

    @Override
    public void downloadFile(String filename) {

        try {

            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, filename));
            System.out.println("Content-Type: "  + s3object.getObjectMetadata().getContentType());
            logger.info("===================== Import File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
    }

    @Override
    public URL uploadFile(String filename, String uploadFilePath) {

        URL fileURL = null;
        try {
            File file = new File(uploadFilePath);
            s3client.putObject(new PutObjectRequest(bucketName, filename, file).withCannedAcl(CannedAccessControlList.PublicRead));
            fileURL = s3client.getUrl(bucketName, filename);
            logger.info("===================== Upload File - Done! =====================");
        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
        return fileURL;
    }

}
