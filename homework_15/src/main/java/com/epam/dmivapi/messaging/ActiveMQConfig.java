package com.epam.dmivapi.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import java.util.Optional;

@EnableJms
@Configuration
public class ActiveMQConfig {
    public static final String ADDENDS_QUEUE = "addends-queue";
    public static final String SUMS_QUEUE = "sums-queue";
    public static final String INVALID_MESSAGES_QUEUE = "invalid_messages-queue";

    private final JmsTemplate jmsTemplate;

    public ActiveMQConfig(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Bean
    DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory, SummingErrorHandler errorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setErrorHandler(errorHandler);
        return factory;
    }

    @Service
    public class SummingErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Throwable t) {
            Throwable exception = Optional.ofNullable(t.getCause()).orElse(t);
            if (exception instanceof InvalidMessageException) {
                jmsTemplate.convertAndSend(
                        INVALID_MESSAGES_QUEUE,
                        exception.getMessage());
            }
        }
    }
}
