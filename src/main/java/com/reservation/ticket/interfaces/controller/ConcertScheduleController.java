package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.application.usecase.ConcertScheduleUsecase;
import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.service.ConcertScheduleService;
import com.reservation.ticket.interfaces.controller.dto.concertSchedule.ConcertScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/concertSchedules")
@RequiredArgsConstructor
public class ConcertScheduleController {

    private final ConcertScheduleService concertScheduleService;
    private final ConcertScheduleUsecase concertScheduleUsecase;

    /**
     * 콘서트 스케줄 목록조회 API - 날짜 선택을 위함
     *  - optional -> 날짜 기간을 한달로 잡고 달력에서 조회할 수 있도록 만들면 유용할거 같다.
     */
    @GetMapping("/concerts/{concertId}/users/{userId}")
    public ResponseEntity<List<ConcertScheduleDto.Response>> getAllConcertSchedules(
            @PathVariable Long concertId,
            @PathVariable Long userId
    ) {
        List<ConcertScheduleCommand.Get> concertSchedules =
                concertScheduleUsecase.selectConcertSchedulesByConcertId(userId, concertId);
        List<ConcertScheduleDto.Response> concertSchedulesResponse =
                concertSchedules.stream().map(ConcertScheduleDto.Response::from).toList();

        return ResponseEntity.ok().body(concertSchedulesResponse);
    }

    /**
     * 콘서트 스케줄러 id로 콘서트 스케즐 정보와 좌석 목록조회 - 자리 선택을 위함
     */
    @GetMapping("/{concertScheduleId}/seats")
    public ResponseEntity<ConcertScheduleDto.ResponseScheduleAndSeats> getConcertSchedule(
            @PathVariable Long concertScheduleId
    ) {
        Long userId = 1L;   // spring security, JWT를 이용한 인증 구현 필요
        ConcertScheduleCommand.GetForSeats concertScheduleWithSeats =
                concertScheduleUsecase.selectSeatsByConcertScheduleId(userId, concertScheduleId);

        ConcertScheduleDto.ResponseScheduleAndSeats response =
                ConcertScheduleDto.ResponseScheduleAndSeats.from(concertScheduleWithSeats);
        return ResponseEntity.ok().body(response);
    }

}
