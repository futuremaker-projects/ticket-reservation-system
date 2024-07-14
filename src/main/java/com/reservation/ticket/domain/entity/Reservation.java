package com.reservation.ticket.domain.entity;

import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserAccount userAccount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'NOT_PAID' COMMENT '지불상태'")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE' COMMENT '예약상태'")
    private ReservationStatus reservationStatus;

    private Integer price;
    private LocalDateTime reservedAt;

    @PrePersist
    public void reservedAt() {
        this.reservedAt = LocalDateTime.now();
        this.paymentStatus = PaymentStatus.NOT_PAID;
        this.reservationStatus = ReservationStatus.ACTIVE;
    }

    public Reservation(UserAccount userAccount, Integer price) {
        this.userAccount = userAccount;
        this.price = price;
    }

    public static Reservation of(UserAccount userAccount, Integer price) {
        return new Reservation(userAccount, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation reservation)) return false;
        return id != null && id.equals(reservation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void changeReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void changePaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
