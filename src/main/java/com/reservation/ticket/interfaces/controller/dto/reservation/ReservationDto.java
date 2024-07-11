package com.reservation.ticket.interfaces.controller.dto.reservation;

import java.util.List;

public class ReservationDto {

    public record Request(Long concertScheduleId, List<Long> seatIds) {
        public static Request of(Long concertScheduleId, List<Long> seatIds) {
            return new Request(concertScheduleId, seatIds);
        }


    }

}
