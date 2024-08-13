package com.reservation.ticket.domain.dto.command;

import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.entity.userAccount.UserAccount;

public class PaymentCommand {

    public record Create(Reservation reservation, UserAccount userAccount) {
        public static Create of(Reservation reservation, UserAccount userAccount) {
            return new Create(reservation, userAccount);
        }
    }

}
