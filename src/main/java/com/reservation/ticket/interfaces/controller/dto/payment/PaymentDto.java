package com.reservation.ticket.interfaces.controller.dto.payment;

public class PaymentDto {
    public record Request(Long reservationId, Long userId) {

    }

}
