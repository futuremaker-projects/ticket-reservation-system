package com.reservation.ticket.domain.dto.info;

import com.reservation.ticket.domain.entity.concert.reservation.Seat;

import java.time.LocalDateTime;

public record SeatInfo(Long id, Long concertScheduleId, LocalDateTime occupiedAt, boolean occupied) {

    public static SeatInfo of(Long id, Long concertScheduleId, LocalDateTime occupiedAt, boolean occupied) {
        return new SeatInfo(id, concertScheduleId, occupiedAt, occupied);
    }

    public static SeatInfo from(Seat seat) {
        return SeatInfo.of(
                seat.getId(), seat.getConcertScheduleId(), seat.getOccupiedAt(), seat.isOccupied()
        );
    }

}
