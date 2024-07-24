package com.reservation.ticket.interfaces.controller.dto;

public class PaymentDto {
    public record Request(Long reservationId) {
        public static Request of(Long reservationId) {
            return new Request(reservationId);
        }
    }

}
