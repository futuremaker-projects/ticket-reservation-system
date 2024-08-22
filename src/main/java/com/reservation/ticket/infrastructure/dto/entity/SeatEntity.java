package com.reservation.ticket.infrastructure.dto.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SeatEntity {

    private Long id;
    private Long concertScheduleId;
    private boolean occupied;
    private LocalDateTime occupiedAt;

    @QueryProjection
    public SeatEntity(Long id, Long concertScheduleId, boolean occupied, LocalDateTime occupiedAt) {
        this.id = id;
        this.concertScheduleId = concertScheduleId;
        this.occupied = occupied;
        this.occupiedAt = occupiedAt;
    }
}
