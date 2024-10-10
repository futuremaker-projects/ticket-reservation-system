package com.reservation.ticket.infrastructure.dto.queue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActiveQueueRedisDto {

    private Long userId;
    private long expireAt;

    public ActiveQueueRedisDto(Long userId, long expireAt) {
        this.userId = userId;
        this.expireAt = expireAt;
    }

    public static ActiveQueueRedisDto of(Long userId, long expireAt) {
        return new ActiveQueueRedisDto(userId, expireAt);
    }

}
