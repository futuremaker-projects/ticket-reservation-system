package com.reservation.ticket.infrastructure.kafka.reservation;

import com.reservation.ticket.domain.entity.concert.reservation.message.ReservationMessage;
import com.reservation.ticket.domain.entity.concert.reservation.message.ReservationMessageSender;
import com.reservation.ticket.infrastructure.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationKafkaMessageSender implements ReservationMessageSender {

    @Value("${kafka.topic.name}")
    private String TOPIC_NAME;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(KafkaMessage<ReservationMessage.Send> message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    @Override
    public void sendMessage(KafkaMessage<String> message) {
        kafkaTemplate.send(TOPIC_NAME, message);
    }

}
