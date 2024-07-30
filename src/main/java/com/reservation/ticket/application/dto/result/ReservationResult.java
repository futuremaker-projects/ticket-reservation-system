package com.reservation.ticket.application.dto.result;

import com.reservation.ticket.domain.dto.info.ReservationInfo;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationResult(Long id, int price, PaymentStatus paymentStatus, ReservationStatus reservationStatus, LocalDateTime reservedAt) {

    public static ReservationResult of(Long id, int price, PaymentStatus paymentStatus, ReservationStatus reservationStatus, LocalDateTime reservedAt) {
        return new ReservationResult(id, price, paymentStatus, reservationStatus, reservedAt);
    }

    public static ReservationResult from(ReservationInfo info) {
        return ReservationResult.of(
                info.id(), info.price(), info.paymentStatus(), info.reservationStatus(), info.reservedAt()
        );
    }

}
