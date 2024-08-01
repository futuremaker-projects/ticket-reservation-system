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
        // 키를 어떻게 가져가야 하나
        this.zSetOps.add(statement.token(), "userId_%d".formatted(statement.userAccount().getId()), System.currentTimeMillis());
        return QueueEntity.of(statement.userAccount(), statement.token(), statement.queueStatus());
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
//        this.zSetOps.
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

    @Override
    public void removeQueue(String token) {
        this.zSetOps.remove(token);
    }

}
