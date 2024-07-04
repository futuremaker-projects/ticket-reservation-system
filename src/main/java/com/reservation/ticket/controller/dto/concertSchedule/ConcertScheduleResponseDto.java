package com.reservation.ticket.controller.dto.concertSchedule;

import com.reservation.ticket.controller.dto.concert.ConcertResponseDto;

import java.time.LocalDateTime;

public record ConcertScheduleResponseDto(Long id,
                                         int amountOfSeat,
                                         LocalDateTime openedAt,
                                         ConcertResponseDto concertInfo) {

    public static ConcertScheduleResponseDto of(
            Long id,
            int amountOfSeat,
            LocalDateTime openedAt,
            ConcertResponseDto concertInfo) {
        return new ConcertScheduleResponseDto(id, amountOfSeat, openedAt, concertInfo);
    }

    public static ConcertScheduleResponseDto of(Long id, LocalDateTime openedAt) {
        return new ConcertScheduleResponseDto(id, 0, openedAt, null);
    }


}
