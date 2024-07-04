package com.reservation.ticket.controller.dto.reservation;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        Long id, Long userId, Long concertScheduleId, Long seatId, Integer price, LocalDateTime reservedAt
) {

    public static ReservationResponseDto of(Long id,
                                            Long userId,
                                            Long concertScheduleId,
                                            Long seatId,
                                            Integer price,
                                            LocalDateTime reservedAt) {
        return new ReservationResponseDto(id, userId, concertScheduleId, seatId, price, reservedAt);
    }

}
