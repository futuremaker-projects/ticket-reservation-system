package com.reservation.ticket.infrastructure.dto.queue.payload;

import com.reservation.ticket.infrastructure.dto.queue.statement.QueueStatement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QueuePayload {

    private Long id;

    private String token;
    private QueueStatement queueStatement;

    private Long userAccountId;

    private LocalDateTime expireAt;
    private LocalDateTime createdAt;

    public QueuePayload(Long id, String token, QueueStatement queueStatement, Long userAccountId, LocalDateTime expireAt, LocalDateTime createdAt) {
        this.id = id;
        this.token = token;
        this.queueStatement = queueStatement;
        this.userAccountId = userAccountId;
        this.expireAt = expireAt;
        this.createdAt = createdAt;
    }

    public static QueuePayload of(String token) {
        return new QueuePayload(null, token, null, null, null, null);
    }
}
