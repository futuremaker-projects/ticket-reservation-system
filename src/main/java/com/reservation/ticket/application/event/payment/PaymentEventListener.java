package com.reservation.ticket.application.event.payment;

import com.reservation.ticket.domain.common.outbox.OutboxRepository;
import com.reservation.ticket.domain.entity.concert.reservation.payment.event.PaymentEvent;
import com.reservation.ticket.domain.entity.concert.reservation.payment.message.PaymentMessage;
import com.reservation.ticket.domain.entity.concert.reservation.payment.message.PaymentMessageSender;
import com.reservation.ticket.infrastructure.dto.statement.OutboxStatement;
import com.reservation.ticket.infrastructure.kafka.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final OutboxRepository outboxRepository;
    private final PaymentMessageSender paymentMessageSender;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveOutbox(PaymentEvent.Send send) {
        OutboxStatement.Save outbox =
                OutboxStatement.Save.of(send.outboxId().toString(), send.paymentId().toString());
        outboxRepository.save(outbox);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessage(PaymentEvent.Send send) {
        PaymentMessage.Send paymentMessage = PaymentMessage.Send.of(send.paymentId());
        KafkaMessage<PaymentMessage.Send> message =
                KafkaMessage.of(send.outboxId().toString(), paymentMessage);
        paymentMessageSender.send(message);
    }

}
