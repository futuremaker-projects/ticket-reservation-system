package com.reservation.ticket.interfaces.controller.dto.payment;

public class PaymentDto {
    public record Request(Long reservationId) {
        public static Request of(Long reservationId) {
            return new Request(reservationId);
        }
    }

}
