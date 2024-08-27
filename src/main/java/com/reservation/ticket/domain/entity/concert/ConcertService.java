package com.reservation.ticket.domain.entity.concert;

import com.reservation.ticket.domain.dto.command.ConcertCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<ConcertCommand.Get> selectAllConcertsByPaging(Pageable pageable) {
        Page<Concert> list = concertRepository.findAll(PageRequest.of(0, pageable.getPageSize()));
        return list.map(ConcertCommand.Get::from);
    }

}
