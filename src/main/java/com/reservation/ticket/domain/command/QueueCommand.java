package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.Queue;
import com.reservation.ticket.domain.enums.QueueStatus;

import java.time.LocalDateTime;

public class QueueCommand {
    public record Get(Long id,
                      UserCommand.Get user,
                      String token,
                      QueueStatus status,
                      LocalDateTime expiredAt,
                      LocalDateTime createdAt
    ) {
        public static Get of(Long id, UserCommand.Get user, String token, QueueStatus status, LocalDateTime expiredAt, LocalDateTime createdAt) {
            return new Get(id, user, token, status, expiredAt, createdAt);
        }

        public static Get from(Queue queue) {
            return Get.of(
                    queue.getId(),
                    UserCommand.Get.form(queue.getUserAccount()),
                    queue.getToken(),
                    queue.getStatus(),
                    queue.getExpiredAt(),
                    queue.getCreatedAt()
            );
        }
    }
}
