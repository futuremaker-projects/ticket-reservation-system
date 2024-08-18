package com.reservation.ticket.domain.entity.concert.reservation.message;

import java.util.List;

public class ReservationMessage {

    public record Send(Long reservationId, Long concertScheduleId, List<Long> seatIds) {
        public static Send of(Long reservationId, Long concertScheduleId, List<Long> seatIds) {
            return new Send(reservationId, concertScheduleId, seatIds);
        }
    }

    public record Get(Long id, Long reservationId, Long concertScheduleId, List<Long> seatIds) {
        public static Get of(Long id, Long reservationId, Long concertScheduleId, List<Long> seatIds) {
            return new Get(id, reservationId, concertScheduleId, seatIds);
        }
    }
}
