package com.reservation.ticket.infrastructure.dto.entity;

import com.reservation.ticket.domain.enums.OutboxType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'INIT' COMMENT '메시지 전송상태'")
    private OutboxType outboxType;

    @Column(columnDefinition = "LONGTEXT COMMENT '메시지'")
    private String message;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    public OutboxEntity(String id, OutboxType outboxType, String message) {
        this.id = id;
        this.outboxType = outboxType;
        this.message = message;
    }

    public static OutboxEntity of(String id, OutboxType outboxType, String message) {
        return new OutboxEntity(id, outboxType, message);
    }

}
