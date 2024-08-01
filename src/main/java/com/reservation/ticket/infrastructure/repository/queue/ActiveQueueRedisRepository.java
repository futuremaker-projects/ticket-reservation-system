package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ActiveQueueRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private SetOperations<String, String> setOperations;

    @PostConstruct
    public void initializingOperations() {
        this.setOperations = redisTemplate.opsForSet();
    }

    public QueueEntity save(QueueStatement statement) {
        this.setOperations.add(statement.queueStatus().name(), "%s".formatted(statement.token()));
        return QueueEntity.of(statement.token());
    }

    public void removeQueues(QueueStatus queueStatus, List<String> queues) {

    }

    public QueueEntity getQueueByToken(String token) {
        String pop = this.setOperations.pop(token);
        return null;
    }


    public void removeQueue(String token) {
        this.setOperations.remove(QueueStatus.ACTIVE.name(), token);
    }

    public List<QueueEntity> getQueuesByStatusPerLimit(QueueStatus queueStatus, int limit) {
        return List.of();
    }
    public List<QueueEntity> getQueuesByStatus(QueueStatus queueStatus) {
        return List.of();
    }
    public int countByStatus(QueueStatus status) {
        return Objects.requireNonNull(this.setOperations.size(status.name())).intValue();
    }
    public QueueEntity getQueueByUserId(Long userId) {
        return null;
    }

    public void verify(String token) {

    }
}
