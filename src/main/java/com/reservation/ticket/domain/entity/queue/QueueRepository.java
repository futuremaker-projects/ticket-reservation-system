package com.reservation.ticket.domain.entity.queue;

import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;

import java.util.List;

public interface QueueRepository {

    List<QueueEntity> getQueuesByStatus(QueueStatus queueStatus);

    List<QueueEntity> getQueuesByStatusPerLimit(QueueStatus queueStatus, int limit);

    QueueEntity getQueueByToken(QueueStatement statement);

    int countByStatus(QueueStatus status);

    QueueEntity save(QueueStatement statement);

    QueueEntity getQueueByUserId(Long userId);

    void removeQueue(QueueStatement statement);

    void removeQueues(QueueStatus queueStatus, List<String> queues);
}
