package com.reservation.ticket.domain.command;

import com.reservation.ticket.domain.entity.Concert;
import com.reservation.ticket.domain.entity.ConcertSchedule;

import java.time.LocalDateTime;

public class ConcertScheduleCommand {
    public record Get(Long id, int limitSeat, LocalDateTime openedAt, Concert concert) {
        public static Get of(Long id, int limitSeat, LocalDateTime openedAt, Concert concert) {
            return new Get(id, limitSeat, openedAt, concert);
        }

        public static Get from(ConcertSchedule concertSchedule) {
            return Get.of(
                concertSchedule.getId(), concertSchedule.getLimitSeat(), concertSchedule.getOpenedAt()
            );
        }

        public static Get of(Long id, int limitSeat, LocalDateTime openedAt) {
            return new Get(id, limitSeat, openedAt, null);
        }

        public static Get fromWithConcert(ConcertSchedule concertSchedule) {
            return Get.of(
                    concertSchedule.getId(), concertSchedule.getLimitSeat(),
                    concertSchedule.getOpenedAt(), concertSchedule.getConcert()
            );
        }
    }

    public record Update() {

    }
}
