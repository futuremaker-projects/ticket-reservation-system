package com.reservation.ticket.application.dto.result;

import com.reservation.ticket.domain.dto.info.SeatInfo;

import java.time.LocalDateTime;
import java.util.List;

public record SeatResult(Long id, Long concertScheduleId, LocalDateTime occupiedAt, boolean occupied) {

    public static SeatResult of(Long id, Long concertScheduleId, LocalDateTime occupiedAt, boolean occupied) {
        return new SeatResult(id, concertScheduleId, occupiedAt, occupied);
    }

    public static SeatResult from(SeatInfo seatInfo) {
        return SeatResult.of(
                seatInfo.id(), seatInfo.concertScheduleId(), seatInfo.occupiedAt(), seatInfo.occupied()
        );
    }

}
