package com.reservation.ticket.infrastructure.kafka.payment;

import com.reservation.ticket.domain.entity.concert.reservation.payment.message.PaymentMessage;
import com.reservation.ticket.domain.entity.concert.reservation.payment.message.PaymentMessageSender;
import com.reservation.ticket.infrastructure.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentKafkaMessageSender implements PaymentMessageSender {

    @Value("${kafka.topic.name}")
    private String TOPIC_NAME;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(KafkaMessage<PaymentMessage.Send> message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

}
