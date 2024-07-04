package com.reservation.ticket.controller.dto.queue;

import java.time.LocalDateTime;

public record QueueResponseDto(Long id,
                               Long userId,
                               String token,
                               TokenStatus status,
                               LocalDateTime createdAt,
                               LocalDateTime expiredAt
) {

    public static QueueResponseDto of(Long id, Long userId, String token, TokenStatus status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        return new QueueResponseDto(id, userId, token, status, createdAt, expiredAt);
    }

}
