package com.reservation.ticket.infrastructure.dto.queue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WaitingQueueRedisDto {

    private Long userId;
    private String token;

    public WaitingQueueRedisDto(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public static WaitingQueueRedisDto of(Long userId, String token) {
        return new WaitingQueueRedisDto(userId, token);
    }
}
