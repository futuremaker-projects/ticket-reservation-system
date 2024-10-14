package com.reservation.ticket.infrastructure.repository.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.exception.ApplicationException;
import com.reservation.ticket.domain.exception.ErrorCode;
import com.reservation.ticket.infrastructure.dto.queue.ActiveQueueRedisDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ActiveQueueRedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private SetOperations<String, String> setOperations;

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void initializingOperations() {
        this.setOperations = redisTemplate.opsForSet();
    }

    /*
        `활성큐`를 저장한다.
            - key : `ACTIVE:token` -> `TTL` 지정을 위한 키 구성 (TTL은 5분)
            - value : `{ userId: 1, expireAt: 1727958218735 }`
    */
    public void save(String token, Long userId) {
        String key = composeKey(token);
        try {
            String value = serializeValue(ActiveQueueRedisDto.of(userId, getCurrentTimeInSeconds(), token));
            this.setOperations.add(key, value);
            redisTemplate.expire(key, 300, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        `token`을 이용하여 `활성큐`의 value를 가져온다.
     */
    public Optional<ActiveQueueRedisDto> getQueueByToken(String token) {
        String key = composeKey(token);
        Set<String> members = this.setOperations.members(key);

        if (members == null || members.isEmpty()) {
            return Optional.empty();
        }
        return members.stream()
                .map(this::deserializeValue)
                .findFirst();
    }

    /*
        `활성큐`의 `TTL`을 연장한다.
            - 활성큐를 찾지 못하면 Exception
            - 활성큐 연장완료: true, 연장안됨: false
     */
    public boolean extendExpiration(String token) {
        Optional<ActiveQueueRedisDto> queueByToken = getQueueByToken(token);
        if (queueByToken.isEmpty()) {
            throw new ApplicationException(ErrorCode.ACTIVE_QUEUE_NOT_FOUND, "active queue not found - token: %s".formatted(token));
        }
        String key = composeKey(token);
        return Boolean.TRUE.equals(redisTemplate.expire(key, 300, TimeUnit.SECONDS));
    }

    public void remove(String token) {
        Optional<ActiveQueueRedisDto> queueByToken = getQueueByToken(token);
        if (queueByToken.isPresent()) {
            try {
                String key = composeKey(token);
                String value = serializeValue(queueByToken.get());
                this.setOperations.remove(key, value);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
        token을 이용하여 활성큐의 존재를 확인한다.
     */
    public boolean verify(String token) {
        Optional<ActiveQueueRedisDto> queueByToken = getQueueByToken(token);
        return queueByToken.isPresent();
    }

    public Long getTtl(String token) {
        String key = composeKey(token);
        return this.redisTemplate.getExpire(key);
    }

    public int countByStatus(QueueStatus status) {
        return Objects.requireNonNull(this.setOperations.size(status.name())).intValue();
    }

    private String serializeValue(ActiveQueueRedisDto activeQueueRedisDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(activeQueueRedisDto);
    }

    private ActiveQueueRedisDto deserializeValue(String value) {
        try {
            return objectMapper.readValue(value, ActiveQueueRedisDto.class);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing value: {}", value, e);
            throw new RuntimeException(e);
        }
    }

    private String composeKey(String token) {
        return "%s:%s".formatted(QueueStatus.ACTIVE.name(), token);
    }

    private long getCurrentTimeInSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
