package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.concertSchedule.ConcertScheduleCommand;
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

    public List<ConcertScheduleCommand.Select> selectAllConcertSchedulesByConcertId(Long concertId) {
        return null;
    }
}
