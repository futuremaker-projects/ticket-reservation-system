package com.reservation.ticket.interfaces.dto;

import com.reservation.ticket.application.dto.criteria.ReservationCriteria;

import java.util.List;

public class ReservationDto {

    public record Request(Long concertScheduleId, List<Long> seatIds, int price) {
        public static Request of(Long concertScheduleId, List<Long> seatIds,int price) {
            return new Request(concertScheduleId, seatIds, price);
        }

        public ReservationCriteria toCriteria() {
            return ReservationCriteria.of(concertScheduleId, seatIds, price);
        }
    }

}
