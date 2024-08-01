package com.reservation.ticket.application.usecase;

import com.reservation.ticket.application.dto.result.ConcertScheduleResult;
import com.reservation.ticket.application.dto.result.SeatResult;
import com.reservation.ticket.domain.dto.info.ConcertScheduleInfo;
import com.reservation.ticket.domain.dto.info.SeatInfo;
import com.reservation.ticket.domain.entity.concert.concertSchedule.ConcertSchedule;
import com.reservation.ticket.domain.entity.concert.concertSchedule.ConcertScheduleService;
import com.reservation.ticket.domain.entity.concert.reservation.SeatService;
import com.reservation.ticket.domain.entity.queue.QueueServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertScheduleUsecase {

    private final QueueServiceImpl queueService;
    private final SeatService seatService;
    private final ConcertScheduleService concertScheduleService;

    /**
     * 1. 대기열 검증
     * 2. 콘서트가 열리는 예약날자가 포함된 모든 스케줄을 조회한다.
     */
    public List<ConcertScheduleResult> selectConcertSchedulesByConcertId(Long concertId, String token) {
        // 토큰 검증
        List<ConcertScheduleInfo> concertSchedules =
                concertScheduleService.selectAllConcertSchedulesByConcertId(concertId);
        queueService.renewQueueExpirationDate(token);
        return concertSchedules.stream().map(ConcertScheduleResult::from).toList();
    }

    /**
     * 1. 대기열 상태 검증
     * 2. 날짜로 선택된 콘서트 스케줄에서 연관된 좌석을 조회한다.
     */
    public List<SeatResult> selectSeatsByConcertScheduleId(Long concertScheduleId, String token) {
        // 콘서트 스케줄 id로 콘서트 스케줄 조회
        ConcertSchedule concertSchedule = concertScheduleService.getConcertScheduleById(concertScheduleId);
        // 콘서트 스케줄에 연관된 좌석 목록조회
        List<SeatInfo> seatInfos = seatService.selectSeatsByConcertScheduleId(concertSchedule.getId());
        // 토큰의 만료시간 5분 연장
        queueService.renewQueueExpirationDate(token);
        return seatInfos.stream().map(SeatResult::from).toList();
    }

}

