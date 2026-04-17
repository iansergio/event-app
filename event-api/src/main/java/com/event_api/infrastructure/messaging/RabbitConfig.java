package com.event_api.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EVENTS_EXCHANGE = "events.exchange";

    public static final String EVENT_CREATED_QUEUE = "event.created.queue";
    public static final String EVENT_STATUS_UPDATED_QUEUE = "event.status.updated.queue";

    public static final String EVENT_CREATED_ROUTING_KEY = "event.created";
    public static final String EVENT_STATUS_UPDATED_ROUTING_KEY = "event.status.updated";

    @Bean
    public TopicExchange eventsExchange() {
        return new TopicExchange(EVENTS_EXCHANGE, true, false);
    }

    @Bean
    public Queue eventCreatedQueue() {
        return new Queue(EVENT_CREATED_QUEUE, true);
    }

    @Bean
    public Queue eventStatusUpdatedQueue() {
        return new Queue(EVENT_STATUS_UPDATED_QUEUE, true);
    }

    @Bean
    public Binding eventCreatedBinding() {
        return BindingBuilder
                .bind(eventCreatedQueue())
                .to(eventsExchange())
                .with(EVENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding eventStatusUpdatedBinding() {
        return BindingBuilder
                .bind(eventStatusUpdatedQueue())
                .to(eventsExchange())
                .with(EVENT_STATUS_UPDATED_ROUTING_KEY);
    }
}
