package com.reservation.ticket.infrastructure.repository.queue;

import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.exception.ApplicationException;
import com.reservation.ticket.domain.exception.ErrorCode;
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
import java.util.Set;

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

    /**
     * members() 를 이용하여 token의 ACTIVE 대기열 정보를 가져옴
     *  데이터가 없으면 null
     */
    public QueueEntity getQueueByToken(String token) {
        Set<String> members = this.setOperations.members(token);
        assert members != null;
        return members.stream()
                .filter(member -> member.equals(token))
                .map(QueueEntity::of)
                .findFirst().orElse(null);
    }

    public void removeQueue(String token) {
        this.setOperations.remove(QueueStatus.ACTIVE.name(), token);
    }

    /**
     * 인터셉터에서 사용예정
     */
    public void verify(String token) {
        QueueEntity queueByToken = getQueueByToken(token);
        if (queueByToken == null) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }
    }

    public int countByStatus(QueueStatus status) {
        return Objects.requireNonNull(this.setOperations.size(status.name())).intValue();
    }
}
