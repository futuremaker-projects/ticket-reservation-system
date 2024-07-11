package com.reservation.ticket.application.usecase;

import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.service.ConcertScheduleService;
import com.reservation.ticket.domain.service.QueueService;
import com.reservation.ticket.domain.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertScheduleUsecase {

    private final QueueService queueService;
    private final SeatService seatService;
    private final ConcertScheduleService concertScheduleService;

    /**
     * 1. 대기열 상태 검증
     * 2. 날짜로 선택된 콘서트 스케줄에서 연관된 자리를 전달한다.
     */
    public ConcertScheduleCommand.GetForSeats selectSeatsByOpenedDate(Long userId, LocalDateTime openedAt) {
        renewQueue(userId);
        // 검증은 여기서
        ConcertScheduleCommand.Get concertSchedule = concertScheduleService.getConcertScheduleByOpenedDate(openedAt);
        List<SeatCommand.Get> seats = seatService.selectSeatsByConcertScheduleId(concertSchedule.id());
        // queue renew 는 여기서 하자
        // 모든 로직이 돌고 renew 하자
        return ConcertScheduleCommand.GetForSeats.of(concertSchedule, seats);
    }

    private void renewQueue(Long userId) {
        queueService.renewExpirationDate(userId);
    }

}
