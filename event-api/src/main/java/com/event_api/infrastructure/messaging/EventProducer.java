package com.event_api.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private final RabbitTemplate rabbitTemplate;

    public EventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EVENTS_EXCHANGE,
                RabbitConfig.EVENT_CREATED_ROUTING_KEY,
                message
        );

        System.out.println(message);
    }

    public void sendNewStatus(String message) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EVENTS_EXCHANGE,
                RabbitConfig.EVENT_STATUS_UPDATED_ROUTING_KEY,
                message
        );

        System.out.println(message);
    }
}
