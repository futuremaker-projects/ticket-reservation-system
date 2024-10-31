package com.reservation.ticket.infrastructure.repository.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.common.outbox.OutboxMessageWriter;
import com.reservation.ticket.domain.common.outbox.OutboxRepository;
import com.reservation.ticket.domain.entity.concert.reservation.message.ReservationMessage;
import com.reservation.ticket.domain.entity.concert.reservation.message.ReservationMessageSender;
import com.reservation.ticket.domain.entity.concert.reservation.payment.message.PaymentMessage;
import com.reservation.ticket.domain.entity.concert.reservation.payment.message.PaymentMessageSender;
import com.reservation.ticket.domain.enums.OutboxType;
import com.reservation.ticket.infrastructure.dto.entity.OutboxEntity;
import com.reservation.ticket.infrastructure.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxMessageWriterImpl implements OutboxMessageWriter {

    private final OutboxRepository outboxRepository;
    private final ReservationMessageSender messageSender;

    private final PaymentMessageSender paymentMessageSender;

    private final ObjectMapper objectMapper;


    @Override
    @Transactional
    public void complete(String outboxId) {
        outboxRepository.updateStatus(outboxId, OutboxType.PUBLISHED);
    }

    @Override
    public void resend() {
        List<OutboxEntity> outboxes = outboxRepository.getOutboxByOutboxType(OutboxType.INIT);
        if (!outboxes.isEmpty()) {
            outboxes.forEach(outbox -> {
                if (outbox.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                    KafkaMessage<String> message = KafkaMessage.of(outbox.getId(), outbox.getMessage());
                    messageSender.sendMessage(message);
                }
            });
        }
    }

    @Override
    public void resendPaymentMessage() {
        List<OutboxEntity> outboxes = outboxRepository.getOutboxByOutboxType(OutboxType.INIT);
        if (!outboxes.isEmpty()) {
            outboxes.forEach(outbox -> {
                if (outbox.getCreatedAt().plusMinutes(5).isBefore(LocalDateTime.now())) {
                    PaymentMessage.Send paymentMessage =
                            objectMapper.convertValue(outbox.getMessage(), PaymentMessage.Send.class);
                    KafkaMessage<PaymentMessage.Send> message = KafkaMessage.of(outbox.getId(), paymentMessage);
                    paymentMessageSender.send(message);
                }
            });
        }
    }

}
