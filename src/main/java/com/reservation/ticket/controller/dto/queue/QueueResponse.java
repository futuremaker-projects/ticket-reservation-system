package com.reservation.ticket.controller.dto.queue;

import java.time.LocalDateTime;

public record QueueResponse(Long id,
                            Long userId,
                            String token,
                            TokenStatus status,
                            LocalDateTime createdAt,
                            LocalDateTime expiredAt
) {

    public static QueueResponse of(Long id, Long userId, String token, TokenStatus status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        return new QueueResponse(id, userId, token, status, createdAt, expiredAt);
    }

}
