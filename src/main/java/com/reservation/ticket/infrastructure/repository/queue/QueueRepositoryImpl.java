package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.entity.queue.QueueRepository;
import com.reservation.ticket.domain.exception.ApplicationException;
import com.reservation.ticket.domain.exception.ErrorCode;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
public class QueueRepositoryImpl {

    private final QueueJpaRepository queueJpaRepository;

    public QueueEntity save(QueueStatement statement) {
        return queueJpaRepository.save(statement.toEntity());
    }

    public QueueEntity getQueueByUserId(Long userId) {
        return queueJpaRepository.findByUserAccount_Id(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.CONTENT_NOT_FOUND,
                        "Queue not found by userId : %d".formatted(userId)));
    }

    public int countByStatus(QueueStatus status) {
        return queueJpaRepository.countQueueByQueueStatus(status);
    }

    public QueueEntity getQueueByToken(String token) {
        return queueJpaRepository.findByToken(token);
    }

    public List<QueueEntity> getQueuesByStatus(QueueStatus queueStatus) {
        return queueJpaRepository.findAllByQueueStatus(queueStatus);
    }

    public List<QueueEntity> getQueuesByStatusPerLimit(QueueStatus queueStatus, int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return queueJpaRepository.findAllByQueueStatusOrderByIdAsc(queueStatus, pageRequest);
    }
}
