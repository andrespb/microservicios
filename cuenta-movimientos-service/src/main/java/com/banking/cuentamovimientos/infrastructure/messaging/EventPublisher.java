package com.banking.cuentamovimientos.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishCuentaCreated(Object event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.BANKING_EXCHANGE,
                RabbitConfig.CUENTA_CREATED_ROUTING_KEY,
                event);
    }

    public void publishCuentaUpdated(Object event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.BANKING_EXCHANGE,
                RabbitConfig.CUENTA_UPDATED_ROUTING_KEY,
                event);
    }

    public void publishMovimientoCreated(Object event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.BANKING_EXCHANGE,
                RabbitConfig.MOVIMIENTO_CREATED_ROUTING_KEY,
                event);
    }
}