package com.reservation.ticket.domain.entity.concert;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConcertRepository {

    List<Concert> findAll();

    Page<Concert> findAll(Pageable pageable);

}
