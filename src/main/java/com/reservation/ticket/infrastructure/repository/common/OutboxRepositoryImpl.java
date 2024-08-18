package com.reservation.ticket.infrastructure.repository.common;

import com.reservation.ticket.domain.common.outbox.OutboxRepository;
import com.reservation.ticket.domain.enums.OutboxType;
import com.reservation.ticket.domain.exception.ApplicationException;
import com.reservation.ticket.domain.exception.ErrorCode;
import com.reservation.ticket.infrastructure.dto.entity.OutboxEntity;
import com.reservation.ticket.infrastructure.dto.statement.OutboxStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {

    private final OutboxJpaRepository outboxJpaRepository;

    @Override
    public OutboxEntity save(OutboxStatement.Save statement) {
        OutboxEntity outbox = OutboxEntity.of(statement.id(), OutboxType.INIT, statement.message());
        return outboxJpaRepository.save(outbox);
    }

    @Override
    public OutboxEntity getOutbox(OutboxStatement.Get get) {
        return outboxJpaRepository.findById(get.id()).orElseThrow(
                () -> new ApplicationException(ErrorCode.CONTENT_NOT_FOUND, "Outbox not found : %s".formatted(get.id())));
    }

    @Override
    public void updateStatus(String outboxId, OutboxType outboxType) {
        outboxJpaRepository.update(outboxId, outboxType);
    }

    @Override
    public List<OutboxEntity> getOutboxByOutboxType(OutboxType outboxType) {
        return outboxJpaRepository.findAllByOutboxType(outboxType);
    }


}
