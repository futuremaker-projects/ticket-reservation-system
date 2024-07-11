package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.Reservation;
import com.reservation.ticket.domain.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationCommand {

    public record Get(Long id, int price, PaymentStatus paymentStatus, LocalDateTime reservedAt) {
        public static Get of(Long id, int price, PaymentStatus paymentStatus, LocalDateTime reservedAt) {
            return new Get(id, price, paymentStatus, reservedAt);
        }

        public static Get from(Reservation reservation) {
            return Get.of(
                    reservation.getId(), reservation.getPrice(),
                    reservation.getPaymentStatus(), reservation.getReservedAt()
            );
        }
    }

    /**
     * usecase 에서 사용
     */
    public record Create(int price, Long concertId, List<Long> seatIds) {
        public static Create of(int price, Long concertId, List<Long> seatIds) {
            return new Create(price, concertId, seatIds);
        }
    }
}
