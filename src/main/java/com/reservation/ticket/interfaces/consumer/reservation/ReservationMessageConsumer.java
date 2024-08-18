package com.reservation.ticket.interfaces.consumer.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.common.outbox.OutboxMessageWriter;
import com.reservation.ticket.domain.dto.command.ReservationCommand;
import com.reservation.ticket.domain.entity.concert.reservation.ReservationService;
import com.reservation.ticket.infrastructure.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.CountDownLatch;

@Component
@RequiredArgsConstructor
public class ReservationMessageConsumer {

    private final ReservationService reservationService;

    private final ObjectMapper objectMapper;
    private final OutboxMessageWriter outboxMessageWriter;

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "group1")
    public void complete(KafkaMessage<?> get) {
        outboxMessageWriter.complete(get.getId());
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "group2")
    public void occupySeats(KafkaMessage<?> get) {
        LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) get.getMessage();
        ReservationCommand.OccupySeats occupySeats = objectMapper.convertValue(map, ReservationCommand.OccupySeats.class);
        reservationService.occupySeats(occupySeats);
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "group3")
    public void testConsumer(KafkaMessage<?> get) {
        payload = get.getId();
        latch = new CountDownLatch(1);
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public String getPayload() {
        return this.payload;
    }
    public CountDownLatch getLatch() {
        return this.latch;
    }

}
