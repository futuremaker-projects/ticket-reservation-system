package com.reservation.ticket.domain.entity.concert.reservation.payment.message;

public class PaymentMessage {

    public record Send(Long paymentId) {
        public static Send of(Long paymentId) {
            return new Send(paymentId);
        }
    }

    public record Get(Long paymentId) {
        public static Get of(Long paymentId) {
            return new Get(paymentId);
        }
    }

}
