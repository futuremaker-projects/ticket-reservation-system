package com.reservation.ticket.infrastructure.dto.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ConcertScheduleEntity {

    private Long id;
    private Integer limitSeat;
    private List<SeatEntity> seats;

    @QueryProjection
    public ConcertScheduleEntity(Long id, Integer limitSeat, List<SeatEntity> seats) {
        this.id = id;
        this.limitSeat = limitSeat;
        this.seats = seats;
    }
}
