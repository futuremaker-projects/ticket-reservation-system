package com.reservation.ticket.controller.dto.seat;

public record SeatResponse(Long id, Long seatId, Long concertScheduleId, boolean occupied) {

    public static SeatResponse of(Long id, Long seatId, Long concertScheduleId, boolean occupied) {
        return new SeatResponse(id, seatId, concertScheduleId, occupied);
    }

}
