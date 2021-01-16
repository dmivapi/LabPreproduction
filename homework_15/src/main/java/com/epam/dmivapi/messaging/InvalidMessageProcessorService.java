package com.epam.dmivapi.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

import static com.epam.dmivapi.messaging.ActiveMQConfig.INVALID_MESSAGES_QUEUE;

@Slf4j
@Component
public class InvalidMessageProcessorService {
    @JmsListener(destination = INVALID_MESSAGES_QUEUE)
    public void receiveAndLog(@Payload String invalidMessage) throws JMSException {
        log.error("Invalid message: {}", invalidMessage);
    }
}
