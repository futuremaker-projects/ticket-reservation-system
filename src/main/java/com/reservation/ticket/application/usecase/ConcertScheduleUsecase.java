package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.command.QueueCommand;
import com.reservation.ticket.domain.command.SeatCommand;
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
    public List<ConcertScheduleCommand.Get> selectConcertSchedulesByConcertId(Long userId, Long concertId) {
        // 토큰 검증
        QueueCommand.Get queue = queueService.verifyQueueByUserId(userId);
        List<ConcertScheduleCommand.Get> concertSchedules =
                concertScheduleService.selectAllConcertSchedulesByConcertId(concertId);
        queueService.renewQueueExpirationDate(queue.token());
        return concertSchedules;
    }

    /**
     * 1. 대기열 상태 검증
     * 2. 날짜로 선택된 콘서트 스케줄에서 연관된 좌석을 조회한다.
     */
    public ConcertScheduleCommand.GetForSeats selectSeatsByConcertScheduleId(Long userId, Long concertScheduleId) {
        // 토큰 검증
        QueueCommand.Get queue = queueService.verifyQueueByUserId(userId);
        ConcertScheduleCommand.Get concertSchedule = concertScheduleService.getConcertScheduleById(concertScheduleId);
        List<SeatCommand.Get> seats = seatService.selectSeatsByConcertScheduleId(concertSchedule.id());
        // 토큰의 만료기간을 5분 늘려줌
        queueService.renewQueueExpirationDate(queue.token());
        return ConcertScheduleCommand.GetForSeats.of(concertSchedule, seats);
    }
}

