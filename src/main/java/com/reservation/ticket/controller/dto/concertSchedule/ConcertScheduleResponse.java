package com.reservation.ticket.controller.dto.concertSchedule;

import com.reservation.ticket.controller.dto.concert.ConcertResponse;

import java.time.LocalDateTime;

public record ConcertScheduleResponse(Long id,
                                      int amountOfSeat,
                                      LocalDateTime openedAt,
                                      ConcertResponse concertInfo) {

    public static ConcertScheduleResponse of(
            Long id,
            int amountOfSeat,
            LocalDateTime openedAt,
            ConcertResponse concertInfo) {
        return new ConcertScheduleResponse(id, amountOfSeat, openedAt, concertInfo);
    }

    public static ConcertScheduleResponse of(Long id, LocalDateTime openedAt) {
        return new ConcertScheduleResponse(id, 0, openedAt, null);
    }


}
