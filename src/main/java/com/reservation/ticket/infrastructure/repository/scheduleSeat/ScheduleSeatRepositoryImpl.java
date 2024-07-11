package com.reservation.ticket.infrastructure.repository.scheduleSeat;

import com.reservation.ticket.domain.entity.complex.ScheduleSeat;
import com.reservation.ticket.domain.repository.ScheduleSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ScheduleSeatRepositoryImpl implements ScheduleSeatRepository {

    private final ScheduleSeatJpaRepository scheduleSeatJpaRepository;

    @Override
    @Transactional
    public ScheduleSeat save(ScheduleSeat scheduleSeat) {
        return scheduleSeatJpaRepository.save(scheduleSeat);
    }
}
