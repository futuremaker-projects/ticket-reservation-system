package com.reservation.ticket.domain.entity.concert.reservation.payment.message;

import com.reservation.ticket.infrastructure.kafka.KafkaMessage;

public interface PaymentMessageSender {

    void send(KafkaMessage<PaymentMessage.Send> message);

}
