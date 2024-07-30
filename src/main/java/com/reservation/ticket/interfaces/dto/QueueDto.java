package com.reservation.ticket.interfaces.dto;

import com.reservation.ticket.domain.dto.command.QueueCommand;
import com.reservation.ticket.domain.enums.QueueStatus;

import java.time.LocalDateTime;

public class QueueDto {
    public record Request(Long userId) {
        public static Request of(Long userId) {
            return new Request(userId);
        }
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

        public static Response from(QueueCommand.Get queueCommand) {
            return Response.of(
                    queueCommand.id(), queueCommand.token(), queueCommand.status(),
                    queueCommand.shouldExpiredAt(), queueCommand.createdAt()
            );
        }
    }
}
