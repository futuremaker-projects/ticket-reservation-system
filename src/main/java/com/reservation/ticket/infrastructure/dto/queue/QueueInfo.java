package com.reservation.ticket.infrastructure.dto.queue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueInfo {

    private Long userId;
    private String token;

    public QueueInfo(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public static QueueInfo of(Long userId, String token) {
        return new QueueInfo(userId, token);
    }
}
