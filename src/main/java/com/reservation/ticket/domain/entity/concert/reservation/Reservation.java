package com.reservation.ticket.domain.entity.concert.reservation;

import com.reservation.ticket.domain.entity.userAccount.UserAccount;
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

    public Reservation(Long id, UserAccount userAccount, Integer price, PaymentStatus paymentStatus, ReservationStatus reservationStatus) {
        this.id = id;
        this.userAccount = userAccount;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
    }

    public static Reservation of(Long id) {
        return new Reservation(id, null, null, null, null);
    }

    public static Reservation of(Long id, UserAccount userAccount, Integer price) {
        return new Reservation(id, userAccount, price, null, null);
    }

    public static Reservation of(UserAccount userAccount, Integer price) {
        return new Reservation(null, userAccount, price, null, null);
    }

    public static Reservation of(UserAccount userAccount, Integer price, PaymentStatus paymentStatus) {
        return new Reservation(null, userAccount, price, paymentStatus, null);
    }

    public static Reservation of(UserAccount userAccount, Integer price, PaymentStatus paymentStatus, ReservationStatus reservationStatus) {
        return new Reservation(null, userAccount, price, paymentStatus, reservationStatus);
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
