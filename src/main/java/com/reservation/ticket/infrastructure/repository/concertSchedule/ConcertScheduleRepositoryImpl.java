package com.reservation.ticket.infrastructure.repository.concertSchedule;

import com.reservation.ticket.domain.entity.concert.concertSchedule.ConcertSchedule;
import com.reservation.ticket.domain.entity.concert.concertSchedule.ConcertScheduleRepository;
import com.reservation.ticket.domain.exception.ApplicationException;
import com.reservation.ticket.domain.exception.ErrorCode;
import com.reservation.ticket.infrastructure.dto.entity.ConcertScheduleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;
    private final ConcertScheduleJpaQuerySupport concertScheduleJpaQuerySupport;

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
                        ErrorCode.CONTENT_NOT_FOUND,
                        "No concert schedule found for opened at %s".formatted(openedAt)));
    }

    @Override
    public ConcertSchedule findById(Long id) {
        return concertScheduleJpaRepository.findById(id).orElseThrow(
                () -> new ApplicationException(
                        ErrorCode.CONTENT_NOT_FOUND,
                        "No concert schedule found for concert schedule id at %d".formatted(id))
        );
    }

    @Override
    public List<ConcertScheduleEntity> getConcertSchedule(Long concertId) {
        return concertScheduleJpaQuerySupport.getConcertSchedulesById(concertId);
    }
}
