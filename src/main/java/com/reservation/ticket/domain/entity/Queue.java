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

    public static Queue of() {
        return new Queue();
    }

    public static Queue of(Long id, UserAccount userAccount, String token, QueueStatus status) {
        return new Queue(id, userAccount, token, status);
    }

    public static Queue of(UserAccount userAccount, String token, QueueStatus status) {
        return new Queue(null, userAccount, token, status);
    }

    @PrePersist
    public void setDates() {
        LocalDateTime now = LocalDateTime.now();
        int expiredMin = 10;

        this.createdAt = now;
        this.shouldExpiredAt = now.plusMinutes(expiredMin);
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

    public void saveData(UserAccount userAccount, String token) {
        this.userAccount = userAccount;
        this.token = token;
    }

    public void changeStatus(QueueStatus queueStatus) {
        this.queueStatus = queueStatus;
    }
}
