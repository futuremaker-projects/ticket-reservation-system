package com.reservation.ticket.application.dto.criteria;

public class PaymentCriteria {

    public record Create(Long reservationId, String token) {
        public static Create of(Long reservationId, String token) {
            return new Create(reservationId, token);
        }
    }

}
