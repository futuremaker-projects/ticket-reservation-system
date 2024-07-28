package com.reservation.ticket.domain.entity.concert;

import java.util.List;

public interface ConcertRepository {

    List<Concert> findAll();

}
