package com.reservation.ticket.infrastructure.dto.queue.statement;

import com.reservation.ticket.domain.entity.queue.Queue;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;

import java.time.LocalDateTime;

/**
 *
 */
public record QueueStatement(Long id,
                             UserAccount userAccount,
                             String token,
                             QueueStatus queueStatus,
                             LocalDateTime shouldExpiredAt,
                             LocalDateTime createdAt
) {

    public static QueueStatement of(Long id,
                                    UserAccount userAccount,
                                    String token,
                                    QueueStatus queueStatus,
                                    LocalDateTime shouldExpiredAt,
                                    LocalDateTime createdAt)
    {
        return new QueueStatement(id, userAccount, token, queueStatus, shouldExpiredAt, createdAt);
    }

    public static QueueStatement of(UserAccount userAccount, String token, QueueStatus queueStatus) {
        return new QueueStatement(null, userAccount, token, queueStatus, null, null);
    }

    public static QueueStatement of(String token, QueueStatus queueStatus) {
        return new QueueStatement(null, null, token, queueStatus, null, null);
    }

    public static QueueStatement of(String token) {
        return new QueueStatement(null, null, token, null, null, null);
    }

    public Queue toEntity() {
        return Queue.of(userAccount, token, queueStatus);
    }
}
