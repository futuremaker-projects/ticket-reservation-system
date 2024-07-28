package com.reservation.ticket.domain.entity.point.pointhistory;

import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) NOT NULL COMMENT '포인트 타입 : 충전, 사용'")
    private TransactionType transactionType;

    private int point;

    private LocalDateTime createdAt;

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    public PointHistory(Long id, UserAccount userAccount, TransactionType transactionType, int point) {
        this.id = id;
        this.userAccount = userAccount;
        this.transactionType = transactionType;
        this.point = point;
    }

    public static PointHistory of(Long id, UserAccount userAccount, TransactionType transactionType, int point) {
        return new PointHistory(id, userAccount, transactionType, point);
    }

    public static PointHistory of(UserAccount userAccount, TransactionType transactionType, int point) {
        return new PointHistory(null, userAccount, transactionType, point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointHistory pointHistory)) return false;
        return id != null && id.equals(pointHistory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
