package com.reservation.ticket.domain.command.concertSchedule;

import com.reservation.ticket.domain.entity.Concert;
import com.reservation.ticket.domain.entity.ConcertSchedule;

import java.time.LocalDateTime;

public class ConcertScheduleCommand {
    public record Select(Long id, int limitSeat, LocalDateTime openedAt, Concert concert) {
        public static Select of(Long id, int limitSeat, LocalDateTime openedAt, Concert concert) {
            return new Select(id, limitSeat, openedAt, concert);
        }

        public static Select from(ConcertSchedule concertSchedule) {
            return Select.of(
                concertSchedule.getId(), concertSchedule.getLimitSeat(), concertSchedule.getOpenedAt()
            );
        }

        public static Select of(Long id, int limitSeat, LocalDateTime openedAt) {
            return new Select(id, limitSeat, openedAt, null);
        }

        public static Select fromWithConcert(ConcertSchedule concertSchedule) {
            return Select.of(
                    concertSchedule.getId(), concertSchedule.getLimitSeat(),
                    concertSchedule.getOpenedAt(), concertSchedule.getConcert()
            );
        }
    }

    public record Update() {

    }
}
