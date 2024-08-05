package com.reservation.ticket.application.dto.result;

import com.reservation.ticket.domain.dto.info.ConcertScheduleInfo;

import java.time.LocalDateTime;

public record ConcertScheduleResult(Long id, int limitSeat, LocalDateTime openedAt) {

    public static ConcertScheduleResult of(Long id, int limitSeat, LocalDateTime openedAt) {
        return new ConcertScheduleResult(id, limitSeat, openedAt);
    }

    public static ConcertScheduleResult from(ConcertScheduleInfo concertScheduleInfo) {
        return ConcertScheduleResult.of(
                concertScheduleInfo.id(), concertScheduleInfo.limitSeat(), concertScheduleInfo.openedAt()
        );
    }
}
