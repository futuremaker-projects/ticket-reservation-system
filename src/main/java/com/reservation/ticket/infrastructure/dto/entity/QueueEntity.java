package com.reservation.ticket.infrastructure.dto.entity;

import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.QueueStatus;
import com.reservation.ticket.domain.exception.ApplicationException;
import com.reservation.ticket.domain.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@ToString(of = {"id", "token", "queueStatus", "shouldExpiredAt", "createdAt"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QueueEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserAccount userAccount;

    private String token;

    @Enumerated(EnumType.STRING)
    private QueueStatus queueStatus;

    private LocalDateTime shouldExpiredAt;
    private LocalDateTime createdAt;

    @PrePersist
    public void setDates() {
        this.createdAt = LocalDateTime.now();
    }

    public QueueEntity(Long id, UserAccount userAccount, String token, QueueStatus queueStatus) {
        this.id = id;
        this.userAccount = userAccount;
        this.token = token;
        this.queueStatus = queueStatus;
    }

    public QueueEntity(Long id, String token, QueueStatus queueStatus, LocalDateTime shouldExpiredAt, LocalDateTime createdAt) {
        this.id = id;
        this.token = token;
        this.queueStatus = queueStatus;
        this.shouldExpiredAt = shouldExpiredAt;
        this.createdAt = createdAt;
    }

    public static QueueEntity of() {
        return new QueueEntity();
    }

    public static QueueEntity of(Long id, String token, QueueStatus status) {
        return new QueueEntity(id, null, token, status);
    }

    public static QueueEntity of(Long id, UserAccount userAccount, String token, QueueStatus status) {
        return new QueueEntity(id, userAccount, token, status);
    }

    public static QueueEntity of(UserAccount userAccount, String token, QueueStatus status) {
        return new QueueEntity(null, userAccount, token, status);
    }

    public static QueueEntity of(UserAccount userAccount, String token) {
        return new QueueEntity(null, userAccount, token, null);
    }

    public static QueueEntity of(Long id, String token, QueueStatus status, LocalDateTime shouldExpiredAt, LocalDateTime createdAt) {
        return new QueueEntity(id, token, status, shouldExpiredAt, createdAt);
    }

    public void saveStatusInQueue(int countActiveStatus) {
        int allowedActiveCount = 30;
        if (countActiveStatus < allowedActiveCount) {
            this.queueStatus = QueueStatus.ACTIVE;
        }
        if (countActiveStatus >= allowedActiveCount) {
            this.queueStatus = QueueStatus.WAIT;
        }
    }

    public void saveData(UserAccount userAccount, String token, QueueStatus queueStatus) {
        this.userAccount = userAccount;
        this.token = token;
        this.queueStatus = queueStatus;
    }

    public void changeStatus(QueueStatus queueStatus) {
        this.queueStatus = queueStatus;
        if (queueStatus == QueueStatus.ACTIVE) {
            this.shouldExpiredAt = LocalDateTime.now();
        }
    }

    public void extendShouldExpiredAt() {
        int extendMin = 5;
        this.shouldExpiredAt = this.shouldExpiredAt.plusMinutes(extendMin);
    }

    public void verifyQueueStatus() {
        if (!(this.queueStatus == QueueStatus.ACTIVE)) {
            throw new ApplicationException(ErrorCode.UNAUTHORIZED, "token queueStatus is not Active : %s".formatted(this.queueStatus));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueueEntity queueEntity)) return false;
        return id != null && id.equals(queueEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
