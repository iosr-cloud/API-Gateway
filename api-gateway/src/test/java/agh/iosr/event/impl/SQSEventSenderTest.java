package agh.iosr.event.impl;

import agh.iosr.event.model.EventMessage;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EventMessage.class)
public class SQSEventSenderTest {


    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private AmazonSQS amazonSQS;

    @InjectMocks
    private SQSEventSender eventSender;

    @Test
    public void sendEvent() throws Exception {

        //given
        final String queueName ="queueName";
        final EventMessage message = PowerMockito.mock(EventMessage.class);
        final String JSON = "{a:1, b:2}";
        ReflectionTestUtils.setField( eventSender,"queueName", queueName);

        //when
        when(objectMapper.writeValueAsString(message)).thenReturn(JSON);
        when(amazonSQS.sendMessage(any(SendMessageRequest.class))).thenReturn(null);
        eventSender.sendEvent(message);

        //then
        verify(objectMapper).writeValueAsString(message);
        verify(amazonSQS).sendMessage(any(SendMessageRequest.class));
    }

}