package com.reservation.ticket.application.event.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.common.outbox.OutboxRepository;
import com.reservation.ticket.domain.entity.concert.reservation.event.ReservationEvent;
import com.reservation.ticket.domain.entity.concert.reservation.message.ReservationMessage;
import com.reservation.ticket.domain.entity.concert.reservation.message.ReservationMessageSender;
import com.reservation.ticket.infrastructure.dto.statement.OutboxStatement;
import com.reservation.ticket.infrastructure.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReservationEventListener {

    private final OutboxRepository outboxRepository;
    private final ReservationMessageSender reservationMessageSender;

    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(ReservationEvent.Success success) throws JsonProcessingException {
        ReservationEvent.Get get =
                ReservationEvent.Get.of(success.reservationId(), success.concertScheduleId(), success.seatIds());
        String message = objectMapper.writeValueAsString(get);
        outboxRepository.save(OutboxStatement.Save.of(success.outboxId(), message));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessage(ReservationEvent.Success success) {
        ReservationMessage.Send send
                = ReservationMessage.Send.of(success.reservationId(), success.concertScheduleId(), success.seatIds());
        KafkaMessage<ReservationMessage.Send> message = KafkaMessage.of(success.outboxId(), send);
        reservationMessageSender.send(message);
    }

}
