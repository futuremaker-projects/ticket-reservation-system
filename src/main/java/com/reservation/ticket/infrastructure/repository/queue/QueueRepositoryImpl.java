package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.queue.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.entity.queue.QueueRepository;
import com.reservation.ticket.domain.exception.ApplicationException;
import com.reservation.ticket.domain.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJpaRepository queueJpaRepository;

    @Override
    public Queue save(Queue queue) {
        return queueJpaRepository.save(queue);
    }

    @Override
    public Queue findQueueByUserId(Long userId) {
        return queueJpaRepository.findByUserAccount_Id(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.CONTENT_NOT_FOUND,
                        "Queue not found by userId : %d".formatted(userId)));
    }

    @Override
    public int countByStatus(QueueStatus status) {
        return queueJpaRepository.countQueueByQueueStatus(status);
    }

    @Override
    public Queue findByToken(String token) {
        return queueJpaRepository.findByToken(token);
    }

    @Override
    public List<Queue> findAllByQueueStatus(QueueStatus queueStatus) {
        return queueJpaRepository.findAllByQueueStatus(queueStatus);
    }

    @Override
    public List<Queue> findAllByQueueStatusPerLimit(QueueStatus queueStatus, int limit) {
        PageRequest pageRequest = PageRequest.of(0, limit);
        return queueJpaRepository.findAllByQueueStatusOrderByIdAsc(queueStatus, pageRequest);
    }
}
