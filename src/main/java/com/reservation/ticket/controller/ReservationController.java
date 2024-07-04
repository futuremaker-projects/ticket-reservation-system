package com.reservation.ticket.controller;

import com.reservation.ticket.controller.dto.concert.ConcertResponseDto;
import com.reservation.ticket.controller.dto.concertSchedule.ConcertScheduleResponseDto;
import com.reservation.ticket.controller.dto.point.PointRequestDto;
import com.reservation.ticket.controller.dto.reservation.ReservationRequestDto;
import com.reservation.ticket.controller.dto.seat.SeatResponseDto;
import com.reservation.ticket.controller.dto.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    /**
     * 콘서트 id로 콘서트 스케줄 목록을 조회한다.
     */
    @GetMapping("/concert/{concertId}/available-date")
    public ResponseEntity<com.reservation.ticket.controller.dto.concert.ConcertResponseDto> selectAvailableDate(@PathVariable Long concertId) {
        return ResponseEntity.ok().body(getConcertScheduleDateInfoList(concertId));
    }

    /**
     * 콘서트 스케줄 id를 이용하여 예약가능한 모든 좌석을 응답한다.
     */
    @GetMapping("/concert-schedules/{concertScheduleId}/available-seat")
    public ResponseEntity<List<SeatResponseDto>> selectAvailableSeat(@PathVariable Long concertScheduleId) {
        return ResponseEntity.ok().body(selectSeatResponseList(concertScheduleId));
    }

    /**
     * 콘서트 스케줄 id와 자리 id를 받아 예약을 진행한다.
     */
    @PostMapping
    public ResponseEntity<Void> reservation(@RequestBody ReservationRequestDto requestDto) {
        Long concertScheduleId = requestDto.concertScheduleId();
        Long seatId = requestDto.seatId();
        return ResponseEntity.ok().build();
    }

    /**
     * 날짜와 자리 선정이 완료되면 포인트를 이용하여 결재한다.
     */
    @PostMapping("/payment")
    public ResponseEntity<Void> makePaymentByPoint(@RequestBody PointRequestDto requestDto) {
        User user = User.of(requestDto.userId(), "name", 1000);
        int subtractedPoint = user.point() - requestDto.point();
        User saveUser = User.of(user.id(), user.name(), subtractedPoint);
        return ResponseEntity.ok().build();
    }

    /**
     * 콘서트 예약시 콘서트의 기본정보 및 콘서트 스케줄정보를 응답해주기 위한 메서드
     */
    public static ConcertResponseDto getConcertScheduleDateInfoList(Long concertId) {
        return ConcertResponseDto.of(
                concertId, "concert1",
                List.of(
                        ConcertScheduleResponseDto.of(1L, LocalDateTime.of(2022, 5, 10, 2, 10)),
                        ConcertScheduleResponseDto.of(2L, LocalDateTime.of(2022, 5, 15, 2, 10)),
                        ConcertScheduleResponseDto.of(3L, LocalDateTime.of(2022, 5, 20, 2, 10))
                )
        );
    }

    /**
     * 콘서트 스케줄 id를 이용하여 모든 좌석을 반환하기 위한 메서드
     */
    public static List<SeatResponseDto> selectSeatResponseList(Long concertScheduleId) {
        Long anotherConcertScheduleId = concertScheduleId + 1;

        List<SeatResponseDto> seatResponseDtos = List.of(
                SeatResponseDto.of(1L, 1L, concertScheduleId, false),
                SeatResponseDto.of(2L, 2L, concertScheduleId, false),
                SeatResponseDto.of(3L, 3L, concertScheduleId, true),
                SeatResponseDto.of(4L, 1L, anotherConcertScheduleId, false),
                SeatResponseDto.of(5L, 2L, anotherConcertScheduleId, false)
        );

        return seatResponseDtos.stream()
                .filter(seat -> seat.concertScheduleId().equals(concertScheduleId) && !seat.occupied())
                .toList();
    }

}
