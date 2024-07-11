package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.entity.complex.ScheduleSeat;
import com.reservation.ticket.domain.entity.complex.ScheduleSeatComplexIds;
import com.reservation.ticket.domain.repository.ScheduleSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleSeatService {

    private final ScheduleSeatRepository scheduleSeatRepository;

    public void save(Long reservationId, Long concertScheduleId, List<Long> seatIds) {
        seatIds.forEach(seatId -> {
            ScheduleSeat scheduleSeat = ScheduleSeat.of(
                    new ScheduleSeatComplexIds(concertScheduleId, seatId), reservationId);
            scheduleSeatRepository.save(scheduleSeat);
        });
    }

}
