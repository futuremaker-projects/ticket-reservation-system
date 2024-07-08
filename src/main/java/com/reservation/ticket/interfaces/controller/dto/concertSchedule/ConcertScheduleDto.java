package com.reservation.ticket.interfaces.controller.dto.concertSchedule;

import com.reservation.ticket.domain.command.concertSchedule.ConcertScheduleCommand;
import com.reservation.ticket.interfaces.controller.dto.concert.ConcertDto;

import java.time.LocalDateTime;

public class ConcertScheduleDto {
    public record Response(Long id, int limitSeat, LocalDateTime openedAt, ConcertDto.Response concert) {
        public static Response of(Long id, int limitSeat, LocalDateTime openedAt, ConcertDto.Response concert) {
            return new Response(id, limitSeat, openedAt, concert);
        }

        public static Response of(Long id, int limitSeat, LocalDateTime openedAt) {
            return new Response(id, limitSeat, openedAt, null);
        }

        public static Response from(ConcertScheduleCommand.Select command) {
            return Response.of(
                    command.id(),
                    command.limitSeat(),
                    command.openedAt()
            );
        }
    }

    public record Request() {

    }
}
