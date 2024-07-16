package com.reservation.ticket.interfaces.controller.dto.queue;

import com.reservation.ticket.domain.enums.QueueStatus;

import java.time.LocalDateTime;

public class QueueDto {
    public record Request(Long userId) {

    }

    public record Response(Long id,
                           String token,
                           QueueStatus status,
                           LocalDateTime shouldExpiredAt,
                           LocalDateTime createdAt
    ) {
        public static Response of(Long id,
                                  String token,
                                  QueueStatus status,
                                  LocalDateTime shouldExpiredAt,
                                  LocalDateTime createdAt) {
            return new Response(id, token, status, shouldExpiredAt, createdAt);
        }
    }
}
