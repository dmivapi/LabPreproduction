package com.epam.dmivapi.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.epam.dmivapi.messaging.ActiveMQConfig.ADDENDS_QUEUE;
import static com.epam.dmivapi.messaging.ActiveMQConfig.SUMS_QUEUE;
import static com.epam.dmivapi.messaging.Application.MSG_NUM_THRESHOLD;

@Slf4j
@Component
public class SumCalculationService {
    private final JmsTemplate jmsTemplate;
    private final Map<String, AddendsMessage> receivedMessages = new HashMap<>();
    private final Random random = new Random(47);

    public SumCalculationService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = ADDENDS_QUEUE)
    public void processAddendsMessage(@Header(JmsHeaders.CORRELATION_ID) String correlationID, @Payload AddendsMessage addends) {
        log.info("Addends received: {} and {}", addends.getFirst(), addends.getSecond());
        receivedMessages.put(correlationID, addends);

        // start actual processing work
        if (receivedMessages.size() == MSG_NUM_THRESHOLD) {
            sumAndSend();
        }
    }

    private void sumAndSend() {
        for (Map.Entry<String, AddendsMessage> entry : receivedMessages.entrySet()) {
            jmsTemplate.convertAndSend(
                    SUMS_QUEUE,
                    sumWithRandomErrors(entry.getValue().getFirst(), entry.getValue().getSecond()),
                    m -> {
                        m.setJMSCorrelationID(mixWithRandomErrors(entry.getKey()));
                        return m;
                    }
            );
        }
    }

    private int sumWithRandomErrors(int arg1, int arg2) {
        return (arg1 + arg2) * (random.nextInt(4) == 0 ? 2 : 1);
    }

    private String mixWithRandomErrors(String arg) {
        return random.nextInt(5) == 0 ? UUID.randomUUID().toString() : arg;
    }
}
