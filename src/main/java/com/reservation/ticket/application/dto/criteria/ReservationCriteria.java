package com.reservation.ticket.application.dto.criteria;

import com.reservation.ticket.domain.dto.command.ReservationCommand;

import java.util.List;

public record ReservationCriteria(Long concertScheduleId, List<Long> seatIds, int price) {

    public static ReservationCriteria of(Long concertScheduleId, List<Long> seatIds, int price) {
        return new ReservationCriteria(concertScheduleId, seatIds, price);
    }

    public ReservationCommand.Create toCreate() {
        return ReservationCommand.Create.of(
                concertScheduleId, seatIds, price
        );
    }

}
