package agh.iosr.event.impl;

import agh.iosr.event.api.EventSender;
import agh.iosr.event.model.EventMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class SQSEventSender implements EventSender {

    @Value("${aws.queue.name}")
    private String queueName;

    private final ObjectMapper mapper;
    private final AmazonSQS amazonSQS;
    private String queueUrl;

    @PostConstruct
    private void init(){
        this.queueUrl = amazonSQS.getQueueUrl(queueName).getQueueUrl();
    }

    @Override
    public void sendEvent(EventMessage message) {

        String JSONObject = convertMessageToJSON(message);
        SendMessageRequest request = createSendMessageRequest(queueUrl, JSONObject);
        amazonSQS.sendMessage(request);

        log.info("Sent message to SQS");
        log.info("Sent message id: " + message.getId());
        log.info("Sent message URL: " + message.getResourceURL());
        log.info("Sent message conversion type: " + message.getConversionType());
    }


    private SendMessageRequest createSendMessageRequest(String queueUrl, String message){
        return new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message);
    }

    private String convertMessageToJSON(EventMessage message){
         try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
