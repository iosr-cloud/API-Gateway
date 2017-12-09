package agh.iosr.event.impl;

import agh.iosr.event.api.EventSender;
import agh.iosr.event.model.EventMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SQSEventSender implements EventSender {

    private Logger logger = LoggerFactory.getLogger(SQSEventSender.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Value("${aws.queue.name}")
    private String queueName;

    private AmazonSQS amazonSQS;
    private String queueUrl;

    public SQSEventSender(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
        this.queueUrl = amazonSQS.getQueueUrl(queueName).getQueueUrl();

    }

    @Override
    public void sendEvent(EventMessage message) {

        String JSONObject = convertMessageToJSON(message);
        SendMessageRequest request = createSendMessageRequest(queueUrl, JSONObject);
        amazonSQS.sendMessage(request);

        logger.info("Sent message to SQS");
        logger.info("Sent message URL: " + message.getResourceURL());
        logger.info("Sent message conversion type: " + message.getConversionType());
    }

    private SendMessageRequest createSendMessageRequest(String queueUrl, String message){
        return new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message);
    }

    //todo better exception handling
    private String convertMessageToJSON(EventMessage message){
         try {
            return mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
