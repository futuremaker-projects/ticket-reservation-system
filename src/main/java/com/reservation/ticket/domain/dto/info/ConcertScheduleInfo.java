package com.reservation.ticket.domain.dto.info;

import com.reservation.ticket.domain.dto.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.entity.concert.Concert;
import com.reservation.ticket.domain.entity.concert.concertSchedule.ConcertSchedule;

import java.time.LocalDateTime;

/**
 * domain - response
 */
public record ConcertScheduleInfo(Long id, int limitSeat, LocalDateTime openedAt, Concert concert) {

    public static ConcertScheduleInfo of(Long id, int limitSeat, LocalDateTime openedAt, Concert concert) {
        return new ConcertScheduleInfo(id, limitSeat, openedAt, concert);
    }

    public static ConcertScheduleInfo of(Long id, int limitSeat, LocalDateTime openedAt) {
        return new ConcertScheduleInfo(id, limitSeat, openedAt, null);
    }

    public static ConcertScheduleInfo from(ConcertSchedule concertSchedule) {
        return ConcertScheduleInfo.of(
                concertSchedule.getId(), concertSchedule.getLimitSeat(), concertSchedule.getOpenedAt()
        );
    }
}
