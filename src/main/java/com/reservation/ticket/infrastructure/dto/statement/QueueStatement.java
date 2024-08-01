package com.reservation.ticket.infrastructure.dto.statement;

import com.reservation.ticket.infrastructure.dto.entity.QueueEntity;
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

    public QueueEntity toEntity() {
        return QueueEntity.of(userAccount, token, queueStatus);
    }
}
