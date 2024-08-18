package com.reservation.ticket.domain.common.outbox;

import com.reservation.ticket.domain.enums.OutboxType;
import com.reservation.ticket.infrastructure.dto.entity.OutboxEntity;
import com.reservation.ticket.infrastructure.dto.statement.OutboxStatement;

import java.util.List;

public interface OutboxRepository {

    List<OutboxEntity> getOutboxByOutboxType(OutboxType outboxType);
    OutboxEntity getOutbox(OutboxStatement.Get get);

    OutboxEntity save(OutboxStatement.Save save);
    void updateStatus(String outboxId, OutboxType outboxType);

}
