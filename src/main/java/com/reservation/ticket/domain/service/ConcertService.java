package com.reservation.ticket.domain.service;

import com.reservation.ticket.domain.command.ConcertCommand;
import com.reservation.ticket.domain.repository.ConcertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;

    public List<ConcertCommand.Select> selectAllConcerts() {
        return concertRepository.findAll().stream()
                .map(ConcertCommand.Select::from)
                .toList();
    }



}
