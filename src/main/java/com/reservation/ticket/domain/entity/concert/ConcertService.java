package com.reservation.ticket.domain.entity.concert;

import com.reservation.ticket.domain.command.ConcertCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertCommand.Get> selectAllConcerts() {
        return concertRepository.findAll().stream()
                .map(ConcertCommand.Get::from)
                .toList();
    }



}
