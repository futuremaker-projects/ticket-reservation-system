package com.reservation.ticket.domain.entity;

import com.reservation.ticket.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserAccount userAccount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10) default 'NOT_PAID' comment '지불상태'")
    private PaymentStatus paymentStatus;

    private LocalDateTime paidAt;

    public Payment(Long id, UserAccount userAccount, Reservation reservation, PaymentStatus paymentStatus, LocalDateTime paidAt) {
        this.id = id;
        this.userAccount = userAccount;
        this.reservation = reservation;
        this.paymentStatus = paymentStatus;
        this.paidAt = paidAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return id != null && id.equals(payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
