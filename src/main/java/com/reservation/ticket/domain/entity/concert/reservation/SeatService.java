package com.reservation.ticket.domain.entity.concert.reservation;

import com.reservation.ticket.domain.dto.command.SeatCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<SeatCommand.Get> selectSeatsByIds(List<Long> seatIds) {
        return seatRepository.findByIdIn(seatIds).stream()
                .map(SeatCommand.Get::from)
                .toList();
    }
}

