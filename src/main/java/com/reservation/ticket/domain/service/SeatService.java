package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.entity.Seat;
import com.reservation.ticket.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    /**
     *  콘서트 스케줄 id로 좌석 목록조회
     */
    public List<SeatCommand.Get> selectSeatsByConcertScheduleId(Long concertScheduleId) {
        return seatRepository.findAllByConcertScheduleId(concertScheduleId).stream()
                .map(SeatCommand.Get::from)
                .toList();
    }

    /**
     *  예약을 기준으로 좌석이 점유되면 occupied 를 false -> ture 변경 및 점유일을 현재시간으로 등록
     */
    public void changeSeatOccupiedStatus(Long reservationId, List<Long> seatIds) {
        List<Seat> seats = seatRepository.findByIdIn(seatIds);
        seats.forEach(seat -> seat.changeToOccupiedAndSaveReservationId(reservationId));
    }

    /**
     * 예약 id로 찾은 좌석들을 모두 점유 해제한다.
     */
    public void recoverSeatOccupiedStatus(List<Long> cancelledReservationIds) {
        List<Seat> occupiedSeats = seatRepository.findAllByReservationIdIn(cancelledReservationIds);
        occupiedSeats.forEach(Seat::releaseOccupiedSeat);
    }

    public List<SeatCommand.Get> selectSeatsByIds(List<Long> seatIds) {
        return seatRepository.findByIdIn(seatIds).stream()
                .map(SeatCommand.Get::from)
                .toList();
    }
}

