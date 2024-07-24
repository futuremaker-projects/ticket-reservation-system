package com.reservation.ticket.interfaces.controller.dto;

import com.reservation.ticket.domain.command.SeatCommand;

import java.time.LocalDateTime;

public class SeatDto {
    public record Response(Long id, Long concertScheduleId,  LocalDateTime occupiedAt, boolean occupied) {
        public static Response of(Long id, Long concertScheduleId, LocalDateTime occupiedAt, boolean occupied) {
            return new Response(id, concertScheduleId, occupiedAt, occupied);
        }

        public static Response from(SeatCommand.Get get) {
            return Response.of(get.id(), get.concertScheduleId(), get.occupiedAt(), get.occupied());
        }
    }

    public record Request() {

    }
}
