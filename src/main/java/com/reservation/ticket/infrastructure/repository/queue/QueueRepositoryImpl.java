package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.queue.Queue;
import com.reservation.ticket.domain.entity.queue.QueueRepository;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.exception.ApplicationException;
import com.reservation.ticket.domain.exception.ErrorCode;
import com.reservation.ticket.infrastructure.dto.queue.statement.QueueStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJpaRepository queueJpaRepository;

    public Queue save(QueueStatement statement) {
        return queueJpaRepository.save(statement.toEntity());
    }

    public Queue getQueueByUserId(Long userId) {
        return queueJpaRepository.findByUserAccount_Id(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.CONTENT_NOT_FOUND,
                        "Queue not found by userId : %d".formatted(userId)));
    }

    @Override
    public void removeQueue(QueueStatement statement) {

    }

    @Override
    public void removeQueues(QueueStatus queueStatus, List<String> queues) {

    }

    public int countByStatus(QueueStatus status) {
        return queueJpaRepository.countQueueByQueueStatus(status);
    }

    @Override
    public Queue getQueueByToken(QueueStatement queueStatement) {
        return queueJpaRepository.findByToken(queueStatement.token());
    }

    public List<Queue> getQueuesByStatus(QueueStatus queueStatus) {
        return queueJpaRepository.findAllByQueueStatus(queueStatus);
    }

    public List<Queue> getQueuesByStatusPerLimit(QueueStatus queueStatus, int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return queueJpaRepository.findAllByQueueStatusOrderByIdAsc(queueStatus, pageRequest);
    }

}
