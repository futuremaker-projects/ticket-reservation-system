package com.reservation.ticket.controller.dto.seat;

public record SeatResponseDto(Long id, Long seatId, Long concertScheduleId, boolean occupied) {

    public static SeatResponseDto of(Long id, Long seatId, Long concertScheduleId, boolean occupied) {
        return new SeatResponseDto(id, seatId, concertScheduleId, occupied);
    }

}
