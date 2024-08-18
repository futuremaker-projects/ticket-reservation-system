package com.reservation.ticket.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.entity.concert.reservation.message.ReservationMessage;
import com.reservation.ticket.infrastructure.kafka.KafkaMessage;
import com.reservation.ticket.infrastructure.kafka.reservation.ReservationKafkaMessageSender;
import com.reservation.ticket.interfaces.consumer.reservation.ReservationMessageConsumer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class EmbeddedKafkaIntegratedTest {

    @Autowired
    ReservationKafkaMessageSender messageSender;

    @Autowired
    ReservationMessageConsumer messageConsumer;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test01() throws InterruptedException {
        // given
        String outboxId = "248c3433f7ac";
        Long reservationId = 1L;
        Long concertScheduleId = 1L;
        List<Long> seatIds = List.of(5L, 6L);
        ReservationMessage.Send send
                = ReservationMessage.Send.of(reservationId, concertScheduleId, seatIds);
        KafkaMessage<ReservationMessage.Send> message = KafkaMessage.of(outboxId, send);

        // when
        messageSender.send(message);

        // then
        messageConsumer.getLatch().await(5, TimeUnit.SECONDS);
        assertThat(messageConsumer.getPayload()).isEqualTo(outboxId);
    }

}
