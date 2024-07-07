package com.reservation.ticket.controller.dto.reservation;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id, Long userId, Long concertScheduleId, Long seatId, Integer price, LocalDateTime reservedAt
) {

    public static ReservationResponse of(Long id,
                                         Long userId,
                                         Long concertScheduleId,
                                         Long seatId,
                                         Integer price,
                                         LocalDateTime reservedAt) {
        return new ReservationResponse(id, userId, concertScheduleId, seatId, price, reservedAt);
    }

}
