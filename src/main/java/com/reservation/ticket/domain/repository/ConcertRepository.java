package com.reservation.ticket.domain.repository;

import com.reservation.ticket.domain.entity.Concert;

import java.util.List;

public interface ConcertRepository {

    List<Concert> findAll();

}
