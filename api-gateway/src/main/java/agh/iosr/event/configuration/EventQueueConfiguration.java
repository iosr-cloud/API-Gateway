package agh.iosr.event.configuration;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueConfiguration {

    @Bean
    public AmazonSQS amazonSQS(){
        return AmazonSQSClientBuilder.defaultClient();
    }

}
