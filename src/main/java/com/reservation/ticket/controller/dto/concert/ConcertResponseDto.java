package com.reservation.ticket.controller.dto.concert;

import com.reservation.ticket.controller.dto.concertSchedule.ConcertScheduleResponseDto;

import java.util.List;

public record ConcertResponseDto(Long id, String name, List<ConcertScheduleResponseDto> concertSchedules) {

    public static ConcertResponseDto of(Long id, String name, List<ConcertScheduleResponseDto> concertSchedules) {
        return new ConcertResponseDto(id, name, concertSchedules);
    }

    public static ConcertResponseDto of(Long id, String name) {
        return new ConcertResponseDto(id, name, null);
    }

}
