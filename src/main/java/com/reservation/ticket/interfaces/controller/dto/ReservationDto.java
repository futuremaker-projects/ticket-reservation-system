package com.reservation.ticket.interfaces.controller.dto;

import com.reservation.ticket.domain.command.ReservationCommand;

import java.util.List;

public class ReservationDto {

    public record Request(Long concertScheduleId, List<Long> seatIds, int price) {
        public static Request of(Long concertScheduleId, List<Long> seatIds,int price) {
            return new Request(concertScheduleId, seatIds, price);
        }

        public ReservationCommand.Create toCreate() {
            return ReservationCommand.Create.of(concertScheduleId, seatIds, price);
        }
    }

}
