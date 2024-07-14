package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.entity.UserAccount;
import com.reservation.ticket.domain.enums.PaymentStatus;

public class PaymentCommand {

    public record Create(UserAccount userAccount, Reservation reservation, PaymentStatus paymentStatus) {
        public Create of(UserAccount userAccount, Reservation reservation, PaymentStatus paymentStatus) {
            return new Create(userAccount, reservation, paymentStatus);
        }
    }

}
