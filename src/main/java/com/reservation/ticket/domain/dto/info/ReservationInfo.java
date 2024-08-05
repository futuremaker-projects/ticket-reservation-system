package com.reservation.ticket.domain.dto.info;

import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationInfo(Long id, int price, PaymentStatus paymentStatus, ReservationStatus reservationStatus, LocalDateTime reservedAt) {

    public static ReservationInfo of(Long id, int price, PaymentStatus paymentStatus, ReservationStatus reservationStatus, LocalDateTime reservedAt) {
        return new ReservationInfo(id, price, paymentStatus, reservationStatus, reservedAt);
    }

    public static ReservationInfo from(Reservation reservation) {
        return ReservationInfo.of(
                reservation.getId(),
                reservation.getPrice(),
                reservation.getPaymentStatus(),
                reservation.getReservationStatus(),
                reservation.getReservedAt()
        );
    }

}
