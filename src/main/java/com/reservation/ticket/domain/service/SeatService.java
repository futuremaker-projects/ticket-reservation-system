package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.SeatCommand;
import com.reservation.ticket.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<SeatCommand.Get> selectSeatsByConcertScheduleId(Long concertScheduleId) {
        return seatRepository.findAllByConcertScheduleId(concertScheduleId).stream()
                .map(SeatCommand.Get::from)
                .toList();
    }

}
