package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.Seat;

import java.time.LocalDateTime;

public class SeatCommand {

    public record Get(Long id, Long concertScheduleId, Long reservationId, LocalDateTime occupiedAt, boolean occupied) {
        public static Get of(
                Long id,
                Long concertScheduleId,
                Long reservationId,
                LocalDateTime occupiedAt,
                boolean occupied
        ) {
            return new Get(id, concertScheduleId, reservationId, occupiedAt, occupied);
        }

        public static Get from(Seat seat) {
            return Get.of(
                    seat.getId(),
                    seat.getConcertScheduleId(),
                    seat.getReservationId(),
                    seat.getOccupiedAt(),
                    seat.isOccupied()
            );
        }
    }
}
