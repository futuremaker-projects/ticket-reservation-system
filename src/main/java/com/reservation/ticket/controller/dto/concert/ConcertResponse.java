package com.reservation.ticket.controller.dto.concert;

import com.reservation.ticket.controller.dto.concertSchedule.ConcertScheduleResponse;

import java.util.List;

public record ConcertResponse(Long id, String name, List<ConcertScheduleResponse> concertSchedules) {

    public static ConcertResponse of(Long id, String name, List<ConcertScheduleResponse> concertSchedules) {
        return new ConcertResponse(id, name, concertSchedules);
    }

    public static ConcertResponse of(Long id, String name) {
        return new ConcertResponse(id, name, null);
    }

}
