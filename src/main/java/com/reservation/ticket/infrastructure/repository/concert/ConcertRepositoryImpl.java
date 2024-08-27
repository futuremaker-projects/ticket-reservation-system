package com.reservation.ticket.infrastructure.repository.concert;

import com.reservation.ticket.domain.entity.concert.Concert;
import com.reservation.ticket.domain.entity.concert.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<Concert> findAll() {
        return concertJpaRepository.findAll();
    }

    @Override
    public Page<Concert> findAll(Pageable pageable) {
        return concertJpaRepository.findAll(pageable);
    }

}
