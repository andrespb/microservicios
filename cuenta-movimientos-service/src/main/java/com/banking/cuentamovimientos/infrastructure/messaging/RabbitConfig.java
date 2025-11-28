package com.banking.cuentamovimientos.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // Exchange names
    public static final String BANKING_EXCHANGE = "banking.exchange";

    // Queue names
    public static final String CUENTA_CREATED_QUEUE = "cuenta.created.queue";
    public static final String CUENTA_UPDATED_QUEUE = "cuenta.updated.queue";
    public static final String MOVIMIENTO_CREATED_QUEUE = "movimiento.created.queue";

    // Routing keys
    public static final String CUENTA_CREATED_ROUTING_KEY = "cuenta.created";
    public static final String CUENTA_UPDATED_ROUTING_KEY = "cuenta.updated";
    public static final String MOVIMIENTO_CREATED_ROUTING_KEY = "movimiento.created";

    @Bean
    public TopicExchange bankingExchange() {
        return new TopicExchange(BANKING_EXCHANGE);
    }

    @Bean
    public Queue cuentaCreatedQueue() {
        return QueueBuilder.durable(CUENTA_CREATED_QUEUE).build();
    }

    @Bean
    public Queue cuentaUpdatedQueue() {
        return QueueBuilder.durable(CUENTA_UPDATED_QUEUE).build();
    }

    @Bean
    public Queue movimientoCreatedQueue() {
        return QueueBuilder.durable(MOVIMIENTO_CREATED_QUEUE).build();
    }

    @Bean
    public Binding cuentaCreatedBinding() {
        return BindingBuilder
                .bind(cuentaCreatedQueue())
                .to(bankingExchange())
                .with(CUENTA_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding cuentaUpdatedBinding() {
        return BindingBuilder
                .bind(cuentaUpdatedQueue())
                .to(bankingExchange())
                .with(CUENTA_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Binding movimientoCreatedBinding() {
        return BindingBuilder
                .bind(movimientoCreatedQueue())
                .to(bankingExchange())
                .with(MOVIMIENTO_CREATED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}