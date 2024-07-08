package com.reservation.ticket.infrastructure.repository.concertSchedule;

import com.reservation.ticket.domain.entity.ConcertSchedule;
import com.reservation.ticket.domain.repository.ConcertScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Override
    public List<ConcertSchedule> findAllConcertSchedules() {
        return concertScheduleJpaRepository.findAll();
    }

    @Override
    public List<ConcertSchedule> findAllByConcertId(Long concertId) {
        return concertScheduleJpaRepository.findAllByConcertId(concertId);
    }
}
