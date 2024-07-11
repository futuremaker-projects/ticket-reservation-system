package com.reservation.ticket.infrastructure.repository.concertSchedule;

import com.reservation.ticket.domain.entity.ConcertSchedule;
import com.reservation.ticket.domain.repository.ConcertScheduleRepository;
import com.reservation.ticket.infrastructure.exception.ApplicationException;
import com.reservation.ticket.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Override
    public ConcertSchedule findByOpenedAt(LocalDateTime openedAt) {
        return concertScheduleJpaRepository.findByOpenedAt(openedAt).orElseThrow(
                () -> new ApplicationException(
                        ErrorCode.CONTENT_NOT_FOUND, "No concert schedule found for opened at %d".formatted(openedAt)));
    }
}
