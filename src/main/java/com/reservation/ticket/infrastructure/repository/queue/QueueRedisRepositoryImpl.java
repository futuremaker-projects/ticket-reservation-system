package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.entity.queue.Queue;
import com.reservation.ticket.domain.entity.queue.QueueRepository;
import com.reservation.ticket.domain.enums.QueueStatus;
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
    public Queue save(Queue queue) {
        /**
         * 키 벨류 스코어
         *
         */
        return zSetOps.add();
    }

    @Override
    public List<Queue> findAllByQueueStatus(QueueStatus queueStatus) {
        return List.of();
    }

    @Override
    public List<Queue> findAllByQueueStatusPerLimit(QueueStatus queueStatus, int limit) {
        return List.of();
    }

    @Override
    public Queue findByToken(String token) {
        return null;
    }

    @Override
    public int countByStatus(QueueStatus status) {
        return 0;
    }

    @Override
    public Queue findQueueByUserId(Long userId) {
        return null;
    }
}
