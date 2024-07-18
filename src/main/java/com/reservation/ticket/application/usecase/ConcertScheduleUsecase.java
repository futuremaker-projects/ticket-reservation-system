package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.entity.ConcertSchedule;
import com.reservation.ticket.domain.service.ConcertScheduleService;
import com.reservation.ticket.domain.service.QueueService;
import com.reservation.ticket.domain.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertScheduleUsecase {

    private final QueueService queueService;
    private final SeatService seatService;
    private final ConcertScheduleService concertScheduleService;

    /**
     * 1. 대기열 검증
     * 2. 콘서트가 열리는 예약날자가 포함된 모든 스케줄을 조회한다.
     */
    public List<ConcertScheduleCommand.Get> selectConcertSchedulesByConcertId(Long concertId, String token) {
        // 토큰 검증
        List<ConcertScheduleCommand.Get> concertSchedules =
                concertScheduleService.selectAllConcertSchedulesByConcertId(concertId);
        queueService.renewQueueExpirationDate(token);
        return concertSchedules;
    }

    /**
     * 1. 대기열 상태 검증
     * 2. 날짜로 선택된 콘서트 스케줄에서 연관된 좌석을 조회한다.
     */
    public List<SeatCommand.Get> selectSeatsByConcertScheduleId(Long concertScheduleId, String token) {
        // 콘서트 스케줄 id로 콘서트 스케줄 조회
        ConcertSchedule concertSchedule = concertScheduleService.getConcertScheduleById(concertScheduleId);
        // 콘서트 스케줄에 연관된 좌석 목록조회
        List<SeatCommand.Get> seats = seatService.selectSeatsByConcertScheduleId(concertSchedule.getId());
        // 토큰의 만료시간 5분 연장
        queueService.renewQueueExpirationDate(token);
        return seats;
    }

}

