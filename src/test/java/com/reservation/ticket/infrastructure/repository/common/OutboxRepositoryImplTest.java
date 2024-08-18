package com.reservation.ticket.infrastructure.repository.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.entity.concert.reservation.event.ReservationEvent;
import com.reservation.ticket.domain.enums.OutboxType;
import com.reservation.ticket.infrastructure.dto.entity.OutboxEntity;
import com.reservation.ticket.infrastructure.dto.statement.OutboxStatement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OutboxRepositoryImplTest {

    @Autowired
    private OutboxRepositoryImpl repository;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("예약 id, 콘서트 스케줄 id, 좌석 ids를 메시지로 한 outbox를 저장 후, 저장된 id를 이용하여 메시지 및 메시지 상태를 확인한다.")
    @Test
    void test01() throws JsonProcessingException {
        // given
        Long reservationId = 1L;
        Long concertScheduleId = 1L;
        List<Long> seatIds = List.of(1L, 2L);
        String outboxId = "734488355d85";
        OutboxType type = OutboxType.INIT;

        ReservationEvent.Success success = ReservationEvent.Success.of(outboxId, reservationId, concertScheduleId, seatIds);
        String message = objectMapper.writeValueAsString(success);

        // when
        OutboxEntity save = repository.save(OutboxStatement.Save.of(outboxId, message));

        // then
        OutboxEntity outbox = repository.getOutbox(OutboxStatement.Get.of(save.getId()));
        assertThat(outbox).isNotNull();
        assertThat(outbox.getOutboxType()).isEqualTo(type);

        ReservationEvent.Success converted = objectMapper.readValue(message, ReservationEvent.Success.class);
        assertThat(converted).isNotNull();
        assertThat(converted.reservationId()).isEqualTo(reservationId);
        assertThat(converted.seatIds()).isEqualTo(seatIds);
        assertThat(converted.concertScheduleId()).isEqualTo(concertScheduleId);
    }

}