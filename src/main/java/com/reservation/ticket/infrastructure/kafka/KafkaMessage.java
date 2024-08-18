package com.reservation.ticket.infrastructure.kafka;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KafkaMessage<T> {

    private String id;
    private T message;

    public KafkaMessage(String id, T message) {
        this.id = id;
        this.message = message;
    }

    public static <T> KafkaMessage<T> of(String id, T message) {
        return new KafkaMessage<>(id, message);
    }

}