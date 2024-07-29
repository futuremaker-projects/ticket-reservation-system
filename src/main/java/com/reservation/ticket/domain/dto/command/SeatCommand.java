package com.reservation.ticket.domain.dto.command;

import com.reservation.ticket.domain.entity.concert.reservation.Seat;

import java.time.LocalDateTime;

public class SeatCommand {

    public record Get(Long id, Long concertScheduleId, LocalDateTime occupiedAt, boolean occupied) {
        public static Get of(
                Long id,
                Long concertScheduleId,
                LocalDateTime occupiedAt,
                boolean occupied
        ) {
            return new Get(id, concertScheduleId, occupiedAt, occupied);
        }

        public static Get from(Seat seat) {
            return Get.of(
                    seat.getId(),
                    seat.getConcertScheduleId(),
                    seat.getOccupiedAt(),
                    seat.isOccupied()
            );
        }
    }
}
