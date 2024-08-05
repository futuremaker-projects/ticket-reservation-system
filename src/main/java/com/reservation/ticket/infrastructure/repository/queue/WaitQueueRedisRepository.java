package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class WaitQueueRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOps;

    @PostConstruct
    public void initializingOperations() {
        this.zSetOps = redisTemplate.opsForZSet();
    }

    public QueueEntity save(QueueStatement statement) {
        // 키를 어떻게 가져가야 하나
        this.zSetOps.add(statement.queueStatus().name(), statement.token(), System.currentTimeMillis());
        return QueueEntity.of(statement.token());
    }

    public List<QueueEntity> getQueuesByStatus(QueueStatus queueStatus) {
        return List.of();
    }

    public List<QueueEntity> getQueuesByStatusPerLimit(QueueStatus queueStatus, int limit) {
        Set<String> values = this.zSetOps.range(queueStatus.name(), 0, limit);
        assert values != null;
        return values.stream().map(QueueEntity::of).toList();
    }

    public QueueEntity getQueueByToken(String token) {
        Set<String> value = this.zSetOps.range("%s".formatted(QueueStatus.WAIT), 0, -1);
        return QueueEntity.of(UserAccount.of(1L), token, QueueStatus.WAIT);
    }

    public int countByStatus(QueueStatus status) {
        return 0;
    }

    public QueueEntity getQueueByUserId(Long userId) {
        return null;
    }

    public void removeQueue(QueueStatus queueStatus, String token) {
        this.zSetOps.remove(queueStatus.name(), token);
    }

    public void removeQueues(QueueStatus queueStatus, List<String> tokens) {
        this.zSetOps.remove(queueStatus.name(), tokens);
    }

}
