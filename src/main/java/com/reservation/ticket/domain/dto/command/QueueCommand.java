package com.reservation.ticket.domain.dto.command;

import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.interfaces.dto.QueueDto;

import java.time.LocalDateTime;

public class QueueCommand {
    public record Get(Long id,
                      UserCommand.Get userAccount,
                      String token,
                      QueueStatus queueStatus,
                      LocalDateTime shouldExpiredAt,
                      LocalDateTime createdAt
    ) {
        public static Get of(Long id, UserCommand.Get user, String token, QueueStatus queueStatus, LocalDateTime shouldExpiredAt, LocalDateTime createdAt) {
            return new Get(id, user, token, queueStatus, shouldExpiredAt, createdAt);
        }

        public static Get of(Long id, String token, QueueStatus queueStatus, LocalDateTime shouldExpiredAt, LocalDateTime createdAt) {
            return new Get(id, null, token, queueStatus, shouldExpiredAt, createdAt);
        }

        public static Get of(Long id, String token, QueueStatus queueStatus) {
            return new Get(id, null, token, queueStatus, null, null);
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
            return QueueDto.Response.of(id, token, queueStatus, shouldExpiredAt, createdAt);
        }
    }
}
