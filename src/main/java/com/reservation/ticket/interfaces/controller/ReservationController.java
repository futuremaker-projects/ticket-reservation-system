package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.application.usecase.ReservationUsecase;
import com.reservation.ticket.interfaces.controller.dto.ReservationDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationUsecase reservationUsecase;

    /**
     * 콘서트 스케줄 id와 자리 id를 받아 예약 생성.
     */
    @PostMapping
    public ResponseEntity<Void> makeReservation(
            @RequestBody ReservationDto.Request requestDto,
            HttpServletRequest request
    ) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        reservationUsecase.makeReservation(requestDto.toCreate(), token);
        return ResponseEntity.ok().build();
    }

}

