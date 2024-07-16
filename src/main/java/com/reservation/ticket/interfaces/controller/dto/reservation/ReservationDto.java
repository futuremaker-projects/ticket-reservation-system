package com.reservation.ticket.interfaces.controller.dto.reservation;

import com.reservation.ticket.domain.command.ReservationCommand;

import java.util.List;

public class ReservationDto {

    public record Request(Long concertScheduleId, List<Long> seatIds, Long userId, int price) {
        public static Request of(Long concertScheduleId, List<Long> seatIds, Long userId, Integer price) {
            return new Request(concertScheduleId, seatIds, userId, price);
        }

        public ReservationCommand.Create toCreate() {
            return ReservationCommand.Create.of(concertScheduleId, seatIds, userId, price);
        }
    }

}
