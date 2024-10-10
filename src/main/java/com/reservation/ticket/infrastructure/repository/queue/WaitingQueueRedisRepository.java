package com.reservation.ticket.infrastructure.repository.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.infrastructure.dto.queue.WaitingQueueRedisDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingQueueRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOperations;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void initializingOperations() {
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    /**
        대기큐를 생성한다.
            - key: 'WAIT'
            - value: { token: 734488355d85, userId: 1L }
                - 예약 시, userId를 사용하기 위해 token과 같이 저장
            - score: 현재 시간, (ex : 1727958218735)
     */
    public void save(Long userId, String token) {
        String value = serialize(WaitingQueueRedisDto.of(userId, token));
        zSetOperations.add(QueueStatus.WAIT.name(), value, getCurrentTimeInSeconds());
    }

    /**
        파라미터 만큼의 범위의 대기큐를 조회한다.
     */
    public Set<WaitingQueueRedisDto> getQueueByRange(int queueCount) {
        Set<String> queues = zSetOperations.range(QueueStatus.WAIT.name(), 0, queueCount - 1);
        if (queues == null) {
            log.info("waiting queue list is empty");
            return null;
        }
        return queues.stream()
                .map(this::deserialize)
                .collect(Collectors.toSet());
    }

    /**
        현재 대기열의 인덱스 순서를 반환한다.
    */
    public Long getRank(String token, Long userId) {
        String value = serialize(WaitingQueueRedisDto.of(userId, token));
        return zSetOperations.rank(QueueStatus.WAIT.name(), value);
    }

    /**
     대기큐를 삭제한다.
     */
    public void remove(String token, Long userId) {
        String value = serialize(WaitingQueueRedisDto.of(userId, token));
        zSetOperations.remove(QueueStatus.WAIT.name(), value);
    }

    private long getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    private String serialize(WaitingQueueRedisDto waitingQueueRedisDto) {
        try {
            return objectMapper.writeValueAsString(waitingQueueRedisDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private WaitingQueueRedisDto deserialize(String value) {
        try {
            return objectMapper.readValue(value, WaitingQueueRedisDto.class);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing value: {}", value, e);
            throw new RuntimeException(e);
        }
    }
}
