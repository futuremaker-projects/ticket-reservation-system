package com.reservation.ticket.controller.dto.concertInfo;

public record ConcertInfoResponseDto(Long id, String name) {

    public static ConcertInfoResponseDto of(Long id, String name) {
        return new ConcertInfoResponseDto(id, name);
    }

}
