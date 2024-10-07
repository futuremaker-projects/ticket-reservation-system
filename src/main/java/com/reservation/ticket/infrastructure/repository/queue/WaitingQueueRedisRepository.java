package com.reservation.ticket.infrastructure.repository.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.queue.QueueInfo;
import com.reservation.ticket.infrastructure.dto.queue.statement.QueueStatement;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingQueueRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOperations;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void initializingOperations() {
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    public void save(Long userId, String token) throws JsonProcessingException {
        // key - token, score - 요청시간, member - 유저정보 (유저id)
        QueueInfo queueInfo = QueueInfo.of(userId, token);
        String value = serializeUserInfo(queueInfo);
        zSetOperations.add(QueueStatus.WAIT.name(), value, getCurrentTimeInSeconds());
    }

    private double getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000d;
    }

    private String serializeUserInfo(QueueInfo queueInfo) throws JsonProcessingException {
        return objectMapper.writeValueAsString(queueInfo);
    }

    private QueueInfo deserializeUserInfo(String value) {
        return objectMapper.convertValue(value, QueueInfo.class);
    }

}
