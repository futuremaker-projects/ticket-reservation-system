package com.reservation.ticket.domain.entity.concert.reservation.event;

import java.util.List;

public class ReservationEvent {

    public record Success(String outboxId, Long reservationId, Long concertScheduleId, List<Long> seatIds) {
        public static Success of(String outboxId, Long reservationId, Long concertScheduleId, List<Long> seatIds) {
            return new Success(outboxId, reservationId, concertScheduleId, seatIds);
        }
    }

    public record Get(Long reservationId, Long concertScheduleId, List<Long> seatIds) {
        public static Get of(Long reservationId, Long concertScheduleId, List<Long> seatIds) {
            return new Get(reservationId, concertScheduleId, seatIds);
        }
    }

}