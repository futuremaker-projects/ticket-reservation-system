package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.queue.QueueRepository;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueueRedisRepositoryImpl implements QueueRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOps;

    @PostConstruct
    public void initializingOperations() {
        this.zSetOps = redisTemplate.opsForZSet();
    }

    @Override
    public QueueEntity save(QueueStatement statement) {
        return null;
    }

    @Override
    public List<QueueEntity> getQueuesByStatus(QueueStatus queueStatus) {
        return List.of();
    }

    @Override
    public List<QueueEntity> getQueuesByStatusPerLimit(QueueStatus queueStatus, int limit) {
        return List.of();
    }

    @Override
    public QueueEntity getQueueByToken(String token) {
        return null;
    }

    @Override
    public int countByStatus(QueueStatus status) {
        return 0;
    }

    @Override
    public QueueEntity getQueueByUserId(Long userId) {
        return null;
    }
}
