package com.reservation.ticket.domain.entity.concert.reservation.message;

import com.reservation.ticket.infrastructure.kafka.KafkaMessage;

public interface ReservationMessageSender {

    void send(KafkaMessage<ReservationMessage.Send> message);

    void sendMessage(KafkaMessage<String> message);

}
