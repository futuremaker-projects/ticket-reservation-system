package com.reservation.ticket.domain.dto.command;

import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.interfaces.dto.QueueDto;

import java.time.LocalDateTime;

public class QueueCommand {
    public record Get(Long id,
                      UserCommand.Get userAccount,
                      String token,
                      QueueStatus status,
                      LocalDateTime shouldExpiredAt,
                      LocalDateTime createdAt
    ) {
        public static Get of(Long id, UserCommand.Get user, String token, QueueStatus status, LocalDateTime shouldExpiredAt, LocalDateTime createdAt) {
            return new Get(id, user, token, status, shouldExpiredAt, createdAt);
        }

        public static Get of(Long id, String token, QueueStatus status, LocalDateTime shouldExpiredAt, LocalDateTime createdAt) {
            return new Get(id, null, token, status, shouldExpiredAt, createdAt);
        }

        public static Get from(QueueEntity queueEntity) {
            return Get.of(
                    queueEntity.getId(),
                    queueEntity.getToken(),
                    queueEntity.getQueueStatus(),
                    queueEntity.getShouldExpiredAt(),
                    queueEntity.getCreatedAt()
            );
        }

        public QueueDto.Response toResponse() {
            return QueueDto.Response.of(id, token, status, shouldExpiredAt, createdAt);
        }
    }
}
