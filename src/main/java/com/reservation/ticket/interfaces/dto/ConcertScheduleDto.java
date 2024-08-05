package com.reservation.ticket.interfaces.dto;

import com.reservation.ticket.application.dto.result.ConcertScheduleResult;
import com.reservation.ticket.domain.dto.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.dto.command.SeatCommand;

import java.time.LocalDateTime;
import java.util.List;

public class ConcertScheduleDto {
    public record Response(Long id, int limitSeat, LocalDateTime openedAt, ConcertDto.Response concert) {
        public static Response of(Long id, int limitSeat, LocalDateTime openedAt, ConcertDto.Response concert) {
            return new Response(id, limitSeat, openedAt, concert);
        }

        public static Response of(Long id, int limitSeat, LocalDateTime openedAt) {
            return new Response(id, limitSeat, openedAt, null);
        }

        public static Response from(ConcertScheduleResult result) {
            return Response.of(
                    result.id(),
                    result.limitSeat(),
                    result.openedAt()
            );
        }
    }

    public record ResponseScheduleAndSeats(ConcertScheduleCommand.Get command, List<SeatCommand.Get> seats) {
        public static ResponseScheduleAndSeats of(ConcertScheduleCommand.Get command, List<SeatCommand.Get> seats) {
            return new ResponseScheduleAndSeats(command, seats);
        }

        public static ResponseScheduleAndSeats from(ConcertScheduleCommand.GetForSeats command) {
            return ResponseScheduleAndSeats.of(command.concertSchedule(), command.seats());
        }
    }

    public record Request() {

    }
}
