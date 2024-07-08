package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.ConcertScheduleCommand;
import com.reservation.ticket.domain.entity.ConcertSchedule;
import com.reservation.ticket.domain.repository.ConcertScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertScheduleService {

    private final ConcertScheduleRepository concertScheduleRepository;

    public List<ConcertSchedule> selectAllConcertSchedules() {
        return concertScheduleRepository.findAllConcertSchedules();
    }

    /**
     *  콘서트 id로 전체 콘서트 스케줄을 불러온다.
     */
    public List<ConcertScheduleCommand.Select> selectAllConcertSchedulesByConcertId(Long concertId) {
        return concertScheduleRepository.findAllByConcertId(concertId).stream()
                .map(ConcertScheduleCommand.Select::from)
                .toList();
    }


}
