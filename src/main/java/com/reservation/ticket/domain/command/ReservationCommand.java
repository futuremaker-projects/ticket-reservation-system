package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.concert.reservation.Reservation;
import com.reservation.ticket.domain.enums.PaymentStatus;
import com.reservation.ticket.domain.enums.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationCommand {

    public record Get(Long id, int price, PaymentStatus paymentStatus, ReservationStatus reservationStatus, LocalDateTime reservedAt) {
        public static Get of(Long id, int price, PaymentStatus paymentStatus, ReservationStatus reservationStatus,  LocalDateTime reservedAt) {
            return new Get(id, price, paymentStatus, reservationStatus, reservedAt);
        }

        public static Get from(Reservation reservation) {
            return Get.of(
                    reservation.getId(), reservation.getPrice(),
                    reservation.getPaymentStatus(), reservation.getReservationStatus(),
                    reservation.getReservedAt()
            );
        }
    }

    /**
     * usecase 에서 사용
     */
    public record Create(Long concertScheduleId, List<Long> seatIds, int price) {
        public static Create of(Long concertScheduleId, List<Long> seatIds, int price) {
            return new Create(concertScheduleId, seatIds, price);
        }
    }
}
