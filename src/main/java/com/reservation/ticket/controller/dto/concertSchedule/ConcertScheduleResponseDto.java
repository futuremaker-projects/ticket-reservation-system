package com.reservation.ticket.controller.dto.concertSchedule;

import java.time.LocalDateTime;

public record ConcertScheduleResponseDto(Long id,
                                         int amountOfSeat,
                                         LocalDateTime openedAt,
                                         com.reservation.ticket.controller.dto.concert.ConcertResponseDto concertInfo) {

    public static ConcertScheduleResponseDto of(
            Long id,
            int amountOfSeat,
            LocalDateTime openedAt,
            com.reservation.ticket.controller.dto.concert.ConcertResponseDto concertInfo) {
        return new ConcertScheduleResponseDto(id, amountOfSeat, openedAt, concertInfo);
    }

    public static ConcertScheduleResponseDto of(Long id, LocalDateTime openedAt) {
        return new ConcertScheduleResponseDto(id, 0, openedAt, null);
    }


}
