package com.reservation.ticket.domain.entity;

import com.reservation.ticket.domain.enums.QueueStatus;
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
public class Queue {

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

    public Queue(Long id, UserAccount userAccount, String token, QueueStatus queueStatus) {
        this.id = id;
        this.userAccount = userAccount;
        this.token = token;
        this.queueStatus = queueStatus;
    }

    public Queue(Long id, String token, QueueStatus queueStatus, LocalDateTime shouldExpiredAt, LocalDateTime createdAt) {
        this.id = id;
        this.token = token;
        this.queueStatus = queueStatus;
        this.shouldExpiredAt = shouldExpiredAt;
        this.createdAt = createdAt;
    }

    public static Queue of() {
        return new Queue();
    }

    public static Queue of(Long id, UserAccount userAccount, String token, QueueStatus status) {
        return new Queue(id, userAccount, token, status);
    }

    public static Queue of(UserAccount userAccount, String token, QueueStatus status) {
        return new Queue(null, userAccount, token, status);
    }

    public static Queue of(Long id, String token, QueueStatus status, LocalDateTime shouldExpiredAt, LocalDateTime createdAt) {
        return new Queue(id, token, status, shouldExpiredAt, createdAt);
    }

    @PrePersist
    public void setDates() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Queue queue)) return false;
        return id != null && id.equals(queue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
        if (this.queueStatus == QueueStatus.ACTIVE) {
            this.shouldExpiredAt = this.shouldExpiredAt.plusMinutes(extendMin);
        }
    }
}
