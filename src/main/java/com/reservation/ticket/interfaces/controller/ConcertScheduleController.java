package com.reservation.ticket.interfaces.controller;

import com.reservation.ticket.application.dto.result.ConcertScheduleResult;
import com.reservation.ticket.application.usecase.ConcertScheduleUsecase;
import com.reservation.ticket.domain.entity.concert.concertSchedule.ConcertScheduleService;
import com.reservation.ticket.infrastructure.dto.entity.ConcertScheduleEntity;
import com.reservation.ticket.interfaces.dto.ConcertScheduleDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    private final ConcertScheduleUsecase concertScheduleUsecase;
    private final ConcertScheduleService concertScheduleService;

    /**
     * 콘서트 스케줄 목록조회 API - 날짜 선택을 위함
     *  - optional -> 날짜 기간을 한달로 잡고 달력에서 조회할 수 있도록 수정필요
     */
    @GetMapping("/concerts/{concertId}")
    public ResponseEntity<List<ConcertScheduleDto.Response>> getAllConcertSchedules(
            @PathVariable Long concertId, HttpServletRequest request
    ) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        List<ConcertScheduleResult> concertSchedules =
                concertScheduleUsecase.selectConcertSchedulesByConcertId(concertId, token);
        List<ConcertScheduleDto.Response> concertSchedulesResponse =
                concertSchedules.stream().map(ConcertScheduleDto.Response::from).toList();

        return ResponseEntity.ok().body(concertSchedulesResponse);
    }

    /**
     *  콘서트 스케줄 id로 콘서트 스케줄 정보 및 연관된 좌석 리스트를 조회한다. (조인)
     */
    @GetMapping("/{concertScheduleId}/seats")
    public ResponseEntity<List<ConcertScheduleEntity>> getSeatsByConcertScheduleId(
            @PathVariable Long concertScheduleId, HttpServletRequest request
    ) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        List<ConcertScheduleEntity> concertSchedule = concertScheduleService.getConcertSchedule(concertScheduleId);
        return ResponseEntity.ok().body(concertSchedule);
    }

//    /**
//     * 콘서트 스케줄러 id로 콘서트 스케즐 정보와 좌석 목록조회 - 자리 선택을 위함
//     */
//    @GetMapping("/{concertScheduleId}/seats")
//    public ResponseEntity<List<SeatDto.Response>> getConcertSchedule(
//            @PathVariable Long concertScheduleId, HttpServletRequest request
//    ) {
////        Long userId = 1L;   // spring security, JWT를 이용한 인증 구현 필요
//        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        List<SeatResult> seatResults = concertScheduleUsecase.selectSeatsByConcertScheduleId(concertScheduleId, token);
//        List<SeatDto.Response> seatResponses = seatResults.stream().map(SeatDto.Response::from).toList();
//        return ResponseEntity.ok().body(seatResponses);
//    }



}

