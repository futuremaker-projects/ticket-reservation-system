package com.reservation.ticket.domain.dto.command;

import com.reservation.ticket.domain.entity.queue.Queue;
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

        public static Get from(Queue queue) {
            return Get.of(
                    queue.getId(),
                    queue.getToken(),
                    queue.getQueueStatus(),
                    queue.getShouldExpiredAt(),
                    queue.getCreatedAt()
            );
        }

        public QueueDto.Response toResponse() {
            return QueueDto.Response.of(id, token, queueStatus, shouldExpiredAt, createdAt);
        }
    }
}
