package com.reservation.ticket.infrastructure.dto.queue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActiveQueueRedisDto {

    private Long userId;
    private long expireAt;
    private String token;

    public ActiveQueueRedisDto(Long userId, long expireAt, String token) {
        this.userId = userId;
        this.expireAt = expireAt;
        this.token = token;
    }

    public static ActiveQueueRedisDto of(Long userId, long expireAt, String token) {
        return new ActiveQueueRedisDto(userId, expireAt, token);
    }

}
