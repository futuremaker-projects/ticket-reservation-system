package com.reservation.ticket.interfaces.dto;

import com.reservation.ticket.application.dto.result.SeatResult;
import com.reservation.ticket.domain.dto.info.SeatInfo;

import java.time.LocalDateTime;

public class SeatDto {
    public record Response(Long id, Long concertScheduleId,  LocalDateTime occupiedAt, boolean occupied) {
        public static Response of(Long id, Long concertScheduleId, LocalDateTime occupiedAt, boolean occupied) {
            return new Response(id, concertScheduleId, occupiedAt, occupied);
        }

        public static Response from(SeatResult result) {
            return Response.of(result.id(), result.concertScheduleId(), result.occupiedAt(), result.occupied());
        }
    }

    public record Request() {

    }
}
