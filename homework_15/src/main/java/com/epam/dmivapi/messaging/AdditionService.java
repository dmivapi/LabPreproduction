package com.epam.dmivapi.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.epam.dmivapi.messaging.ActiveMQConfig.ADDENDS_QUEUE;
import static com.epam.dmivapi.messaging.ActiveMQConfig.SUMS_QUEUE;

@Slf4j
@Service
public class AdditionService {
    private final JmsTemplate jmsTemplate;
    private final Map <String, AddendsMessage> sentMessages = new HashMap<>();

    public AdditionService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendAdditionRequestMessage(AddendsMessage argMsg) {
        log.info("Sending addition arguments: {} and {}", argMsg.getFirst(), argMsg.getSecond());

        UUID correlationId = UUID.randomUUID();
        jmsTemplate.convertAndSend(
                ADDENDS_QUEUE,
                argMsg,
                m -> {
                    m.setJMSCorrelationID(correlationId.toString());
                    return m;
                }
        );
        sentMessages.put(correlationId.toString(), argMsg);
    }

    @JmsListener(destination = SUMS_QUEUE)
    public void getAdditionResultMessage(@Header(JmsHeaders.CORRELATION_ID) String correlationID,
                                         @Payload Integer sum) {
        AddendsMessage args =
                Optional.ofNullable(sentMessages.get(correlationID)).orElseThrow(
                        () -> new InvalidMessageException("Summing response came with unknown correlation Id: " + correlationID)
                );
        log.info("The sum received for {} and {} arguments is: {}", args.getFirst(), args.getSecond(), sum);

        if (args.getFirst() + args.getSecond() != sum) {
            throw new InvalidMessageException("Wrong sum calculation: " + args.getFirst() + " + " + args.getSecond() + " = " + sum);
        }
    }
}
