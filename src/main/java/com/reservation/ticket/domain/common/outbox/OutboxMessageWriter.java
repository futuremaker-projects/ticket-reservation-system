package com.reservation.ticket.domain.common.outbox;

public interface OutboxMessageWriter {

    void complete(String outboxId);

    void resend();
}
