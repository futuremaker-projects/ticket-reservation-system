package com.reservation.ticket.controller.dto.concert;

import com.reservation.ticket.controller.dto.concertInfo.ConcertInfoResponseDto;

import java.time.LocalDateTime;

public record ConcertResponseDto(Long id,
                                 int amountOfSeat,
                                 LocalDateTime openedAt,
                                 ConcertInfoResponseDto concertInfo) {

    public static ConcertResponseDto of(
            Long id,
            int amountOfSeat,
            LocalDateTime openedAt,
            ConcertInfoResponseDto concertInfo) {
        return new ConcertResponseDto(id, amountOfSeat, openedAt, concertInfo);
    }

}
