package agh.iosr.event.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueConfiguration {

    @Value("${aws.queue.access_key_id}")
    private String awsId;

    @Value("${aws.queue.secret}")
    private String awsKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public AmazonSQS amazonSQS(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
        AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        return sqsClient;
    }

}
