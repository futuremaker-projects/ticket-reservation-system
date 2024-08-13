package com.reservation.ticket.domain.entity.concert.reservation.payment;

import com.reservation.ticket.domain.entity.userAccount.UserAccount;
import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserAccount userAccount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Reservation reservation;

    private LocalDateTime paidAt;

    @PrePersist
    public void paidAt() {
        this.paidAt = LocalDateTime.now();
    }

    public Payment(Long id, UserAccount userAccount, Reservation reservation, LocalDateTime paidAt) {
        this.id = id;
        this.userAccount = userAccount;
        this.reservation = reservation;
        this.paidAt = paidAt;
    }

    public static Payment of(Long id, UserAccount userAccount, Reservation reservation, LocalDateTime paidAt) {
        return new Payment(id, userAccount, reservation, paidAt);
    }

    public static Payment of(Long id, UserAccount userAccount, Reservation reservation) {
        return new Payment(id, userAccount, reservation, null);
    }

    public static Payment of(UserAccount userAccount, Reservation reservation) {
        return new Payment(null, userAccount, reservation, null);
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
