package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.queue.QueueRepository;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.infrastructure.dto.statement.QueueStatement;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class WaitQueueRedisRepositoryImpl implements QueueRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOps;

    @PostConstruct
    public void initializingOperations() {
        this.zSetOps = redisTemplate.opsForZSet();
    }

    @Override
    public QueueEntity save(QueueStatement statement) {
        // 키를 어떻게 가져가야 하나
        this.zSetOps.add("%s".formatted(statement.queueStatus()),
                "%s:%d".formatted(statement.token(), statement.userAccount().getId()),
                System.currentTimeMillis()
        );
        return QueueEntity.of(statement.userAccount(), statement.token(), statement.queueStatus());
    }

    @Override
    public List<QueueEntity> getQueuesByStatus(QueueStatus queueStatus) {
        return List.of();
    }

    @Override
    public List<QueueEntity> getQueuesByStatusPerLimit(QueueStatus queueStatus, int limit) {
        Set<String> values = this.zSetOps.range(queueStatus.name(), 0, limit);
        assert values != null;
        return values.stream().map(content -> {
            String token = content.split(":")[0];
            long userId = Long.parseLong(content.split(":")[1]);
            return QueueEntity.of(UserAccount.of(userId), token);
        }).toList();
    }

    @Override
    public QueueEntity getQueueByToken(String token) {
        Set<String> value = this.zSetOps.range("%s".formatted(QueueStatus.WAIT), 0, -1);
        System.out.println("value = " + value);
        return QueueEntity.of(UserAccount.of(1L), token, QueueStatus.WAIT);
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
