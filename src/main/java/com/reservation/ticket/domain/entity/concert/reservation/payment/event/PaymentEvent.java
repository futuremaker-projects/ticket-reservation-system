package com.reservation.ticket.domain.entity.concert.reservation.payment.event;

import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.concert.reservation.payment.Payment;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;

public class PaymentEvent {

    public record Success(Reservation reservation, UserAccount userAccount, Payment payment) {
        public static Success of(Reservation reservation, UserAccount userAccount, Payment payment) {
            return new Success(reservation, userAccount, payment);
        }
    }

    public record Recover(Long reservationId, Long paymentId, Long userId) {
        public static Recover of(Long reservationId, Long paymentId, Long userId) {
            return new Recover(reservationId, paymentId, userId);
        }
    }

}
